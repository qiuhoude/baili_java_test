package com.houde.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author qiukun
 * @create 2019-08-07 19:18
 */
public class Producer {
    private final RingBuffer<OrderEvent> ringBuffer;

    public Producer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer data) {
        //1 在生产者发送消息的时候, 首先 需要从我们的ringBuffer里面 获取一个可用的序号
        long sequence = ringBuffer.next();
        try {
            //2 根据这个序号, 找到具体的 "OrderEvent" 元素 注意:此时获取的OrderEvent对象是一个没有被赋值的"空对象"
            OrderEvent event = ringBuffer.get(sequence);
            //3 进行实际的赋值处理
            event.setValue(data.getLong(0));
        } finally {
            //4 提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}
