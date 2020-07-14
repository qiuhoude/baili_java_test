package com.houde.disruptor.hight.multi;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qiukun
 * @create 2019-09-20 18:49
 */
public class Main2 {
    static AtomicLong threadId = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory tf = r -> {
            Thread t = new Thread(r);
            t.setName("order-thread-" + threadId.incrementAndGet());
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (Thread.NORM_PRIORITY != t.getPriority()) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        };
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(OrderEvent.FACTORY, 4, tf, ProducerType.MULTI, new YieldingWaitStrategy());
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }
        disruptor.handleEventsWithWorkerPool(consumers);
        RingBuffer<OrderEvent> ringBuffer = disruptor.start();
        final int num = 3;
        for (int i = 0; i < num; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < num; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        System.err.println("任务总数:" + consumers[1].getCount());

    }
}
