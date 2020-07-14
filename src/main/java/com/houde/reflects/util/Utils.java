package com.houde.reflects.util;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.houde.reflects.Reflect;
import com.houde.reflects.resource.Resource;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author qiukun
 * @create 2020-07-14 10:42
 */

public class Utils {

    /**
     * @param suffix
     * @return
     */
    public static Predicate<Resource> predicateResourceNameSuffix(final String suffix) {
        return Predicates.compose(new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.endsWith(suffix);
            }
        }, new Function<Resource, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Resource input) {
                return input.getName();
            }
        });
    }

    public static Predicate<Resource> predicateResourceNamePattern(final String pattern) {
        return Predicates.compose(Predicates.containsPattern(pattern), new Function<Resource, CharSequence>() {
            @Nullable
            @Override
            public CharSequence apply(@Nullable Resource input) {
                return input.getName();
            }
        });
    }

    public static Predicate<Class<?>> predicateClassSubTypeOf(final Class<?> clazz) {
        return new Predicate<Class<?>>() {
            @Override
            public boolean apply(@Nullable Class<?> input) {
                return input != clazz && clazz.isAssignableFrom(input);
            }
        };
    }

    public static Predicate<Class<?>> predicateClassAnnotateWith(final Class<? extends Annotation> annotation) {
        if (!annotation.isAnnotationPresent(Inherited.class)) {
            return predicateAnnotateWith(annotation);
        }

        return new Predicate<Class<?>>() {
            @Override
            public boolean apply(@Nullable Class<?> input) {
                while (input != null) {
                    if (input.isAnnotationPresent(annotation)) {
                        return true;
                    }

                    input = input.getSuperclass();
                }

                return false;
            }
        };
    }

    public static <T extends AnnotatedElement> Predicate<T>
    predicateAnnotateWith(final Class<? extends Annotation> annotation) {
        return new Predicate<T>() {
            @Override
            public boolean apply(@Nullable T input) {
                return input.isAnnotationPresent(annotation);
            }
        };
    }

    public static ImmutableSet<Class<?>> toClasses(Iterable<Resource> resources) {
        return FluentIterable.from(resources).transform(new Function<Resource, Class<?>>() {
            @Nullable
            @Override
            public Class<?> apply(@Nullable Resource input) {
                String s = input.getName();
                s = s.substring(0, s.length() - ".class".length());
                s = s.replace('/', '.');

                try {
                    return input.getLoader().loadClass(s);
                } catch (ClassNotFoundException e) {
                    throw new ReflectException(e);
                }
            }
        }).toSet();
    }

    public static ImmutableSet<Field> toFields(Iterable<Class<?>> classes) {
        return FluentIterable.from(classes).transformAndConcat(new Function<Class<?>, ImmutableSet<Field>>() {
            @Nullable
            @Override
            public ImmutableSet<Field> apply(@Nullable Class<?> input) {
                return Reflect.fields(input);
            }
        }).toSet();
    }

    public static ImmutableSet<Method> toMethods(Iterable<Class<?>> classes) {
        return FluentIterable.from(classes).transformAndConcat(new Function<Class<?>, ImmutableSet<Method>>() {
            @Nullable
            @Override
            public ImmutableSet<Method> apply(@Nullable Class<?> input) {
                return Reflect.methods(input);
            }
        }).toSet();
    }

    public static ImmutableSet<Constructor<?>> toConstructors(Iterable<Class<?>> classes) {
        return FluentIterable.from(classes).transformAndConcat(new Function<Class<?>, ImmutableSet<Constructor<?>>>() {
            @Nullable
            @Override
            public ImmutableSet<Constructor<?>> apply(@Nullable Class<?> input) {
                return Reflect.constructors(input);
            }
        }).toSet();
    }

    public static int combineModifiers(int... modifiers) {
        int r = 0;

        for (int modifier : modifiers) {
            r |= modifier;
        }

        return r;
    }

}
