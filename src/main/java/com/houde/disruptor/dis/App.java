package com.houde.disruptor.dis;

import com.houde.disruptor.quickstart.ObjectEvent;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * @author qiukun
 * @create 2019-07-29 13:13
 */
public class App {

    public static void handleEvent1(ObjectEvent event, long sequence, boolean endOfBatch) {
        System.out.println("handler-1: " + event.getObject());
    }

    public static void handleEvent2(ObjectEvent event, long sequence, boolean endOfBatch) {
        System.out.println("handler-2: " + event.getObject());
    }

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;
        Disruptor<ObjectEvent> disruptor = new Disruptor(ObjectEvent::new, bufferSize, Executors.defaultThreadFactory(),
                ProducerType.SINGLE, new LiteBlockingWaitStrategy());

        // 消费者
        disruptor.handleEventsWith(App::handleEvent1);
        disruptor.handleEventsWith(App::handleEvent2);
        disruptor.start();


        //生产者
        new Thread(() -> {
            RingBuffer<ObjectEvent> ringBuffer = disruptor.getRingBuffer();
            for (long l = 0; l < 100; l++) {
                String obj = "Test-" + l;

                ringBuffer.publishEvent((event, sequence) -> event.setObject(obj));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}

