package com.houde.disruptor.quickstart;

/**
 * @author qiukun
 * @create 2019-07-29 12:50
 */
public class ObjectEvent {

    private Object object;

    public Object getObject() {
        return object;
    }

    public ObjectEvent setObject(Object object) {
        this.object = object;
        return this;
    }

}
