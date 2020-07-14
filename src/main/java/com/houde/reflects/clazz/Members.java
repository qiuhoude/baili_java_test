package com.houde.reflects.clazz;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

/**
 * 字节码 包含的成员抽象
 *
 * @author qiukun
 * @create 2020-07-14 11:12
 */
public abstract class Members<E extends Member & AnnotatedElement, M extends Members<E, M>>
        extends AnnotatedElements<E, M> {
    protected Members(ImmutableSet<E> set) {
        super(set);
    }

    @Override
    protected int getModifier(E e) {
        return e.getModifiers();
    }
}
