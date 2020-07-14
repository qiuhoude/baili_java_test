package com.houde.reflects.resource;

import com.google.common.collect.ImmutableSet;
import com.houde.reflects.Filterable;
import com.houde.reflects.clazz.Classes;

import static com.houde.reflects.util.Utils.*;

/**
 * @author qiukun
 * @create 2020-07-14 10:47
 */
public class Resources extends Filterable<Resource, Resources> {
    public static Resources of(ImmutableSet<Resource> resources) {
        return new Resources(resources);
    }

    private Resources(ImmutableSet set) {
        super(set);
    }

    /**
     * 根据suffix过滤资源，其中Resource.name满足后缀是suffix
     *
     * @param suffix
     * @return
     */
    public Resources suffix(String suffix) {
        return filter(predicateResourceNameSuffix(suffix));
    }

    /**
     * 根据pattern过滤资源，其中Resource.name可以匹配pattern
     *
     * @param pattern
     * @return
     */
    public Resources pattern(String pattern) {
        return filter(predicateResourceNamePattern(pattern));
    }

    /**
     * 过滤字节码文件 并使用类加装器加装字节码，构造Classes返回
     *
     * @return
     */
    public Classes classes() {
        return Classes.of(toClasses(suffix(".class")));
    }
}
