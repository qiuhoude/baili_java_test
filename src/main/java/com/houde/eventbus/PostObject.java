package com.houde.eventbus;

/**
 * Created by I on 2017/7/26.
 */
public class PostObject {
    /**
     * 优先级
     */
    public int priority;
    public EventLister eventLister;

    public PostObject() {
    }

    public PostObject(int priority, EventLister eventLister) {
        this.priority = priority;
        this.eventLister = eventLister;
    }
}
