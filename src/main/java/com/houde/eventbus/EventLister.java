package com.houde.eventbus;

/**
 * Created by I on 2017/7/26.
 */
public interface EventLister<T> {
    void postResult(T eventVaule);
}
