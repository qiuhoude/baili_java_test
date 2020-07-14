package com.houde.reflects;


import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.houde.reflects.util.ReflectException;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 筛选抽象类
 *
 * @author qiukun
 * @create 2020-07-14 10:41
 */
public abstract class Filterable<E, F extends Filterable<E, F>> implements Iterable<E>, Supplier<ImmutableSet<E>> {
    /**
     * 待筛选的不可变集合
     */
    private final ImmutableSet<E> set;

    protected Filterable(ImmutableSet<E> set) {
        checkNotNull(set);

        this.set = set;
    }

    /**
     * 使用predicate条件筛选集合
     *
     * @param predicate
     * @return
     */
    public final F filter(Predicate<E> predicate) {
        return with(FluentIterable.from(set).filter(predicate).toSet());
    }

    /**
     * 使用set集合 初始化Filterable对象返回
     *
     * @param set
     * @return
     */
    public F with(ImmutableSet<E> set) {
        try {
            Constructor<F> constructor = (Constructor<F>) this.getClass().getDeclaredConstructor(ImmutableSet.class);
            constructor.setAccessible(true);

            return constructor.newInstance(set);
        } catch (Exception e) {
            throw new ReflectException(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public ImmutableSet<E> get() {
        return set;
    }

    public final int size() {
        return set.size();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).add("size", size()).toString();
    }
}
