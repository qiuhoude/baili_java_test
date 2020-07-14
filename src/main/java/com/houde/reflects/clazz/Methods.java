package com.houde.reflects.clazz;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Method;

/**
 * 字节码Methods集合
 *
 * @author qiukun
 * @create 2020-07-14 10:49
 */
public class Methods extends Members<Method, Methods> {
    public static Methods of(ImmutableSet<Method> methods) {
        return new Methods(methods);
    }

    private Methods(ImmutableSet<Method> set) {
        super(set);
    }
}
