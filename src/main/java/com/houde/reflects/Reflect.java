package com.houde.reflects;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 反射工具类
 *
 * @author qiukun
 * @create 2020-07-14 10:39
 */
public class Reflect {
    /**
     * 返回clazz继承的所有父类、实现的所有接口 集合
     *
     * @param clazz
     * @return
     */
    public static ImmutableSet<Class<?>> superTypes(Class<?> clazz) {
        return superTypes(clazz, Predicates.<Class<?>>alwaysTrue());
    }

    /**
     * 返回class继承的所有父类、实现的所有接口 集合，并且这些父类、接口满足predicate的条件
     *
     * @param clazz
     * @param predicate
     * @return
     */
    public static ImmutableSet<Class<?>> superTypes(Class<?> clazz, Predicate<Class<?>> predicate) {
        checkNotNull(clazz);
        checkNotNull(predicate);

        ImmutableSet.Builder<Class<?>> builder = ImmutableSet.builder();
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            if (predicate.apply(superClass)) {
                builder.add(superClass);
            }

            builder.addAll(superTypes(superClass, predicate));
        }

        Class<?>[] interfaceClasses = clazz.getInterfaces();
        if (interfaceClasses != null) {
            for (Class<?> interfaceClass : interfaceClasses) {
                if (predicate.apply(interfaceClass)) {
                    builder.add(interfaceClass);
                }

                builder.addAll(superTypes(interfaceClass, predicate));
            }
        }

        return builder.build();
    }

    /**
     * 返回class的所有字段集合，包括父类的
     *
     * @param clazz
     * @return
     */
    public static ImmutableSet<Field> fields(Class<?> clazz) {
        return fields(clazz, Predicates.<Field>alwaysTrue());
    }

    /**
     * 返回class的所有字段集合，包括父类的，并且满足predicate的条件
     *
     * @param clazz
     * @param predicate
     * @return
     */
    public static ImmutableSet<Field> fields(Class<?> clazz, Predicate<Field> predicate) {
        checkNotNull(clazz);
        checkNotNull(predicate);

        ImmutableSet.Builder<Field> builder = ImmutableSet.builder();
        for (Field field : clazz.getDeclaredFields()) {
            if (predicate.apply(field)) {
                builder.add(field);
            }
        }

        for (Class<?> superType : superTypes(clazz)) {
            for (Field field : superType.getDeclaredFields()) {
                if (predicate.apply(field)) {
                    builder.add(field);
                }
            }
        }

        return builder.build();
    }

    /**
     * 返回class的所有方法集合，包括父类的
     *
     * @param clazz
     * @return
     */
    public static ImmutableSet<Method> methods(Class<?> clazz) {
        return methods(clazz, Predicates.<Method>alwaysTrue());
    }

    /**
     * 返回class的所有方法集合，包括父类的，并且满足predicate的条件
     *
     * @param clazz
     * @param predicate
     * @return
     */
    public static ImmutableSet<Method> methods(Class<?> clazz, Predicate<Method> predicate) {
        checkNotNull(clazz);
        checkNotNull(predicate);

        ImmutableSet.Builder<Method> builder = ImmutableSet.builder();
        for (Method method : clazz.getDeclaredMethods()) {
            if (predicate.apply(method)) {
                builder.add(method);
            }
        }

        for (Class<?> superType : superTypes(clazz)) {
            for (Method method : superType.getDeclaredMethods()) {
                if (predicate.apply(method)) {
                    builder.add(method);
                }
            }
        }

        return builder.build();
    }

    /**
     * 返回class的所有构造方法集合，包括父类的
     *
     * @param clazz
     * @return
     */
    public static ImmutableSet<Constructor<?>> constructors(Class<?> clazz) {
        return constructors(clazz, Predicates.<Constructor<?>>alwaysTrue());
    }

    /**
     * 返回class的所有构造方法集合，包括父类的，并且满足predicate的条件
     *
     * @param clazz
     * @param predicate
     * @return
     */
    public static ImmutableSet<Constructor<?>> constructors(Class<?> clazz, Predicate<Constructor<?>> predicate) {
        checkNotNull(clazz);
        checkNotNull(predicate);

        ImmutableSet.Builder<Constructor<?>> builder = ImmutableSet.builder();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (predicate.apply(constructor)) {
                builder.add(constructor);
            }
        }

        for (Class<?> superType : superTypes(clazz)) {
            for (Constructor<?> constructor : superType.getDeclaredConstructors()) {
                if (predicate.apply(constructor)) {
                    builder.add(constructor);
                }
            }
        }

        return builder.build();
    }

    /**
     * 将包 转换成路径表示
     *
     * @param pkg
     * @return
     */
    public static String pkgToResPath(String pkg) {
        return pkg.replace('.', '/');
    }

    /**
     * 将包格式的字符串数组 转换成路径表示
     *
     * @param pkgs
     * @return
     */
    public static String[] pkgToResPath(String... pkgs) {
        String[] result = new String[pkgs.length];
        for (int i = 0; i < pkgs.length; i++) {
            result[i] = pkgToResPath(pkgs[i]);
        }

        return result;
    }
}
