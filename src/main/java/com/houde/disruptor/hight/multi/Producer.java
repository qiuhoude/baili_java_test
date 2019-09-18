package com.houde.disruptor.hight.multi;

import com.lmax.disruptor.RingBuffer;

/**
 * @author qiukun
 * @create 2019-08-08 16:37
 */
public class Producer {
    private RingBuffer<OrderEvent> ringBuffer;

    public Producer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(String uuid) {
        long sequence = ringBuffer.next();
        try {
            OrderEvent order = ringBuffer.get(sequence);
            order.setId(uuid);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
