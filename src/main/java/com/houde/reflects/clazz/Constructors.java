package com.houde.reflects.clazz;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Constructor;

/**
 * 字节码构造方法集合
 *
 * @author qiukun
 * @create 2020-07-14 11:29
 */
public class Constructors extends Members<Constructor<?>, Constructors> {

    public static Constructors of(ImmutableSet<Constructor<?>> constructors) {
        return new Constructors(constructors);
    }

    private Constructors(ImmutableSet<Constructor<?>> constructors) {
        super(constructors);
    }
}
