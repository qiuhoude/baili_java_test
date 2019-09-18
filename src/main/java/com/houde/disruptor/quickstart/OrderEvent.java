package com.houde.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * @author qiukun
 * @create 2019-08-07 19:08
 */
public class OrderEvent {
    private long value; //订单的价格

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public final static EventFactory<OrderEvent> EVENT_FACTORY = OrderEvent::new;
}
