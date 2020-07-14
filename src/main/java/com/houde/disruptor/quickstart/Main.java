package com.houde.disruptor.quickstart;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author qiukun
 * @create 2019-08-06 20:01
 */
public class Main {
    static AtomicLong threadId = new AtomicLong(0);

    public static void main(String[] args) {

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
        /**
         * 1 eventFactory: 消息(event)工厂对象
         * 2 ringBufferSize: 容器的长度
         * 3 executor: 线程池(建议使用自定义线程池) RejectedExecutionHandler
         * 4 ProducerType: 单生产者 还是 多生产者
         * 5 waitStrategy: 等待策略
         */
        //1. 实例化disruptor对象
        Disruptor<OrderEvent> disruptor = new Disruptor<>(OrderEvent.EVENT_FACTORY,
                4, tf, ProducerType.SINGLE, new BusySpinWaitStrategy());

        //2. 添加消费者的监听 (构建disruptor 与 消费者的一个关联关系)
//        disruptor.handleEventsWith(new OrderEventHandler()).handleEventsWith(new OrderEventHandler());
        disruptor.handleEventsWithWorkerPool(new OrderWorkHandler(1), new OrderWorkHandler(2));


        //3. 启动disruptor
        //4. 获取实际存储数据的容器: RingBuffer
        RingBuffer<OrderEvent> ringBuffer = disruptor.start();
        Producer producer = new Producer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long i = 1; i < 20; i++) {
            bb.putLong(0, i);
            producer.sendData(bb);
        }

        disruptor.shutdown();
    }


    static class OrderEventHandler implements EventHandler<OrderEvent> {
        private final int id;

        OrderEventHandler(int id) {
            this.id = id;
        }

        @Override
        public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
            System.out.println(Thread.currentThread().getName() + " event消费者-" + id + " :" + event.getValue());
        }
    }

    static class OrderWorkHandler implements WorkHandler<OrderEvent> {

        private final int id;

        OrderWorkHandler(int id) {
            this.id = id;
        }

        @Override
        public void onEvent(OrderEvent event) throws Exception {
            System.out.println(Thread.currentThread().getName() + " work消费者-" + id + " :" + event.getValue());
        }
    }

}
