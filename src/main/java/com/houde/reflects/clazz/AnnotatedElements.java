package com.houde.reflects.clazz;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.houde.reflects.Filterable;
import com.houde.reflects.util.Utils;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;


/**
 * 类元素抽象
 *
 * @author qiukun
 * @create 2020-07-14 10:54
 */
public abstract class AnnotatedElements<E extends AnnotatedElement, A extends AnnotatedElements<E, A>>
        extends Filterable<E, A> {
    protected AnnotatedElements(ImmutableSet<E> set) {
        super(set);
    }

    public A annotatedWith(final Class<? extends Annotation> annotation) {
        return filter(Utils.<E>predicateAnnotateWith(annotation));
    }

    public final A modifiers(int... modifiers) {
        final int m = Utils.combineModifiers(modifiers);

        return filter(new Predicate<E>() {

            @Override
            public boolean apply(@Nullable E input) {
                return (getModifier(input) & m) == m;
            }
        });
    }

    protected abstract int getModifier(E e);
}
