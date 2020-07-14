package com.houde.reflects.util;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 路径过滤匹配类
 *
 * @author qiukun
 * @create 2020-07-14 10:44
 */
public class PathFilter {
    /**
     * 构造PathFilter对象
     *
     * @param paths
     * @param recursive
     * @param keepLeadingSlash
     * @return
     */
    public static PathFilter of(ImmutableSet<String> paths, boolean recursive, boolean keepLeadingSlash) {
        return new PathFilter(paths, recursive, keepLeadingSlash);
    }

    /**
     * 需要匹配的路径 不可变集合
     */
    private final ImmutableSet<String> paths;

    /**
     * 是否递归匹配
     */
    private final boolean recursive;
    /**
     * 是否保持"/"开头
     */
    private final boolean keepLeadingSlash;

    private PathFilter(ImmutableSet<String> paths, boolean recursive, boolean keepLeadingSlash) {
        checkNotNull(paths);

        this.paths = normalizePaths(paths, keepLeadingSlash);
        this.recursive = recursive;
        this.keepLeadingSlash = keepLeadingSlash;
    }

    public boolean isValid(String path) {
        path = pathDir(path);
        if (!recursive) {
            return paths.contains(path);
        }

        for (String s : paths) {
            if (path.startsWith(s)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回path所在目录
     *
     * @param path
     * @return
     */
    private String pathDir(String path) {
        int idx = path.lastIndexOf("/");
        if (idx < 0) {
            return keepLeadingSlash ? "/" : "";
        } else {
            return path.substring(0, idx + 1);
        }
    }

    /**
     * 检查paths，返回合法的路径
     *
     * @param paths
     * @param keepLeadingSlash
     * @return
     */
    private static ImmutableSet<String> normalizePaths(ImmutableSet<String> paths, boolean keepLeadingSlash) {
        return FluentIterable.from(paths).transform(new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String s) {
                // 过滤空白符
                s = CharMatcher.whitespace().trimFrom(s);
                s = CharMatcher.is('/').trimFrom(s);

                int l = s.length();
                if (keepLeadingSlash) {
                    s = "/" + s;
                }
                return l > 0 ? s + "/" : s;
            }
        }).toSet();
    }
}
