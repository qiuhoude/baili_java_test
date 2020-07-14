package com.houde.reflects.clazz;

import com.google.common.collect.ImmutableSet;
import com.houde.reflects.util.Utils;

import java.lang.annotation.Annotation;

/**
 * 类文件集合
 *
 * @author qiukun
 * @create 2020-07-14 10:56
 */
public class Classes extends AnnotatedElements<Class<?>, Classes> {
    public static Classes of(ImmutableSet<Class<?>> classes) {
        return new Classes(classes);
    }

    private Classes(ImmutableSet<Class<?>> classes) {
        super(classes);
    }

    public Classes subTypeOf(Class<?> clazz) {
        return filter(Utils.predicateClassSubTypeOf(clazz));
    }

    @Override
    public Classes annotatedWith(final Class<? extends Annotation> annotation) {
        return filter(Utils.predicateClassAnnotateWith(annotation));
    }

    @Override
    protected int getModifier(Class<?> clazz) {
        return clazz.getModifiers();
    }

    public Fields fields() {
        return Fields.of(Utils.toFields(this));
    }

    public Methods methods() {
        return Methods.of(Utils.toMethods(this));
    }

    public Constructors constructors() {
        return Constructors.of(Utils.toConstructors(this));
    }
}
