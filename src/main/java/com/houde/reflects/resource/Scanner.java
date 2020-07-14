package com.houde.reflects.resource;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.houde.reflects.Reflect;
import com.houde.reflects.util.PathFilter;
import com.houde.reflects.util.ReflectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 资源扫描类
 *
 * @author qiukun
 * @create 2020-07-14 10:45
 */
public class Scanner {
    private static final Logger logger = LoggerFactory.getLogger(Scanner.class);

    /**
     * 空格字符分割工具
     */
    private static final Splitter CLASS_PATH_ATTRIBUTE_SPLITTER = Splitter.on(" ").trimResults().omitEmptyStrings();

    /**
     * 通过包名 构造扫描类
     *
     * @param pkgs
     * @return
     */
    public static Scanner pkgs(String... pkgs) {
        return paths(Reflect.pkgToResPath(pkgs));
    }

    /**
     * 通过路径 构造扫描类
     *
     * @param paths
     * @return
     */
    public static Scanner paths(String... paths) {
        return from(ImmutableSet.of(Thread.currentThread().getContextClassLoader()), ImmutableSet.copyOf(paths), true);
    }

    /**
     * 构造扫描类
     *
     * @param classLoaders
     * @param paths
     * @param recursive
     * @return
     */
    public static Scanner from(ImmutableSet<ClassLoader> classLoaders, ImmutableSet<String> paths, boolean recursive) {
        return new Scanner(classLoaders, paths, recursive);
    }

    /**
     * 待扫描的类加装器集合
     */
    private final ImmutableSet<ClassLoader> classLoaders;

    /**
     * 使用的路径过滤匹配类
     */
    private final PathFilter pathFilter;

    private Scanner(ImmutableSet<ClassLoader> classLoaders, ImmutableSet<String> paths, boolean recursive) {
        checkNotNull(classLoaders);

        this.classLoaders = classLoaders;
        this.pathFilter = PathFilter.of(paths, recursive, false);
    }

    /**
     * 路径过滤后 满足条件的资源集合
     *
     * @return
     */
    public Resources scan() {
        return Resources.of(new ScanJob().scan());
    }

    /**
     * 执行扫描逻辑的Job类，暂存所有满足条件的资源
     */
    private class ScanJob {
        /**
         * 满足pathFilter条件的资源集合
         */
        private final ImmutableSet.Builder<Resource> builder;

        /**
         * 已经扫描过的文件集合
         */
        private final Set<File> scannedFiles;

        ScanJob() {
            this.builder = ImmutableSet.builder();
            this.scannedFiles = Sets.newHashSet();
        }

        public ImmutableSet<Resource> scan() {
            for (ClassLoader classLoader : classLoaders) {
                if (!(classLoader instanceof URLClassLoader)) {
                    logger.error("Illegal class loader, currently only URLClassLoader is supported!{}", classLoader);
                    continue;
                }

                URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
                for (URL url : urlClassLoader.getURLs()) {
                    try {
                        scan(url, classLoader);
                    } catch (Exception e) {
                        throw new ReflectException(e);
                    }
                }
            }

            return builder.build();
        }

        /**
         * 使用loader类加装器 扫描url资源
         *
         * @param url
         * @param loader
         * @throws Exception
         */
        private void scan(URL url, ClassLoader loader) throws Exception {
            if (!url.getProtocol().equals("file")) {
                logger.error("Illegal url, currently only file:// url is supported! {}", url);
                return;
            }

            File file = new File(url.toURI());
            file = file.getCanonicalFile();

            if (!scannedFiles.add(file)) {
                return;
            }

            if (!file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                scanDir(file, loader);
            } else {
                scanJar(file, loader);
            }
        }

        private void scanDir(File dir, ClassLoader loader) {
            scanDir(dir, loader, "");
        }

        /**
         * 递归扫描目录及文件
         *
         * @param dir
         * @param loader
         * @param path
         */
        private void scanDir(File dir, ClassLoader loader, String path) {
            File[] files = dir.listFiles();
            if (files == null) {
                return;
            }

            for (File file : files) {
                String pathName = path + file.getName();
                if (file.isDirectory()) {
                    scanDir(file, loader, pathName + "/");
                } else {
                    if (pathFilter.isValid(pathName)) {
                        builder.add(Resource.of(pathName, loader));
                    }
                }
            }
        }

        private void scanJar(File file, ClassLoader loader) throws Exception {
            try (JarFile jarFile = new JarFile(file)) {
                scanJar(jarFile, loader);
                scanJarManifest(file, jarFile.getManifest(), loader);
            }
        }

        /**
         * 扫描添加 jar包中满足pathFilter条件的资源实体
         *
         * @param jarFile
         * @param loader
         */
        private void scanJar(JarFile jarFile, ClassLoader loader) {
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                JarEntry entry = e.nextElement();
                if (!entry.isDirectory() && pathFilter.isValid(entry.getName())) {
                    builder.add(Resource.of(entry.getName(), loader));
                }
            }
        }

        /**
         * 扫描jar Manifest文件中Class-Path定义的jar
         *
         * @param file
         * @param manifest
         * @param loader
         * @throws Exception
         */
        private void scanJarManifest(File file, Manifest manifest, ClassLoader loader) throws Exception {
            if (manifest == null) {
                return;
            }

            String classPath = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH);
            if (Strings.isNullOrEmpty(classPath)) {
                return;
            }

            for (String path : CLASS_PATH_ATTRIBUTE_SPLITTER.split(classPath)) {
                URL url = new URL(file.toURI().toURL(), path);
                scan(url, loader);
            }
        }
    }
}
