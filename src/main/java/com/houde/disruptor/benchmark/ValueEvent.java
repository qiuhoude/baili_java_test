package com.houde.disruptor.benchmark;

import com.lmax.disruptor.EventFactory;

/**
 * @author qiukun
 * @create 2019-07-29 14:11
 */
public class ValueEvent {
    private long value;
    public long getValue()
    {
        return value;
    }
    public void setValue(final long value)
    {
        this.value = value;
    }
    public static final EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>()
    {
        public ValueEvent newInstance()
        {
            return new ValueEvent();
        }
    };
}
