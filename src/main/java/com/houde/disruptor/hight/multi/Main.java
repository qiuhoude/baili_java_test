package com.houde.disruptor.hight.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;

/**
 * @author qiukun
 * @create 2019-08-08 15:50
 */
public class Main {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        //1 创建RingBuffer
        RingBuffer<OrderEvent> ringBuffer = RingBuffer.create(ProducerType.MULTI, OrderEvent.FACTORY,
                1024 * 1024, new YieldingWaitStrategy());

        //2 通过ringBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //3 创建多个消费者数组:
        Consumer[] consumers = new Consumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("C" + i);
        }

        // 4 构建多消费者工作池
        WorkerPool<OrderEvent> workerPool = new WorkerPool<>(ringBuffer, sequenceBarrier,
                new EventExceptionHandler(), consumers);

        //5 设置多个消费者的sequence序号 用于单独统计消费进度, 并且设置到ringbuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //6 启动workerPool
        workerPool.start(Executors.newFixedThreadPool(10));
        final int num = 3;

        CyclicBarrier cyclicBarrier = new CyclicBarrier(num + 1);

        for (int i = 0; i < num; i++) {
            final Producer producer = new Producer(ringBuffer);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < num; j++) {
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }
        cyclicBarrier.await();
        System.err.println("----------线程创建完毕，开始生产数据----------" + cyclicBarrier.getNumberWaiting());
        cyclicBarrier.await();
        System.err.println("任务总数:" + consumers[2].getCount());

    }


    static class EventExceptionHandler implements ExceptionHandler<OrderEvent> {
        public void handleEventException(Throwable ex, long sequence, OrderEvent event) {
            System.out.println("-> handleEventException " + ex);
        }

        public void handleOnStartException(Throwable ex) {
            System.out.println("-> handleOnStartException " + ex);
        }

        public void handleOnShutdownException(Throwable ex) {
            System.out.println("-> handleOnShutdownException " + ex);
        }
    }
}
