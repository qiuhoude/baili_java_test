package com.houde.eventbus;

/**
 * Created by I on 2017/7/26.
 */
public class EventKey {
    /**
     * 广播编号
     */
    public String code;
    /**
     * 优先级
     */
    public int priority;
    /**
     * id标识
     */
    public long id;

    public EventKey(String code, int priority, long id) {
        this.code = code;
        this.priority = priority;
        this.id = id;
    }
}
