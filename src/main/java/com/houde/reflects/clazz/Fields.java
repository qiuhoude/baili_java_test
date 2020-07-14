package com.houde.reflects.clazz;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;

/**
 * 字节码field集合
 *
 * @author qiukun
 * @create 2020-07-14 10:58
 */
public class Fields extends Members<Field, Fields> {
    public static Fields of(ImmutableSet<Field> fields) {
        return new Fields(fields);
    }

    private Fields(ImmutableSet<Field> set) {
        super(set);
    }
}
