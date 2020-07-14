package com.houde.reflects.resource;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * classpath路径下需要加载的资源，可以是配置文件、资源文件、字节码文件
 *
 * @author qiukun
 * @create 2020-07-14 10:47
 */
public class Resource {
    /**
     * 构造Resource对象
     *
     * @param name
     * @param loader
     * @return
     */
    public static Resource of(String name, ClassLoader loader) {
        return new Resource(name, loader);
    }

    /**
     * 资源对应的名字
     */
    private final String name;

    /**
     * 当前使用的classLoader
     */
    private final ClassLoader loader;

    private Resource(String name, ClassLoader loader) {
        checkArgument(!Strings.isNullOrEmpty(name));
        checkNotNull(loader);

        this.name = name;
        this.loader = loader;
    }

    public String getName() {
        return name;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resource) {
            Resource res = (Resource) obj;
            return name.equals(res.name) && loader == res.loader;
        }

        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).add("name", name).add("loader", loader).toString();
    }
}
