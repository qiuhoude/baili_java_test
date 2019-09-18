package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author qiukun
 * @create 2019-08-07 19:27
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Disruptor<Trade> disruptor = new Disruptor<>(Trade.FACTORY, 1024 * 1024, new MyThreadFactory(),
                ProducerType.MULTI, new BusySpinWaitStrategy());


        // 串行操作：
//     disruptor.handleEventsWith(new Handler1())
//                .handleEventsWith(new Handler2())
//                .handleEventsWith(new Handler3());
//

        // 并行操作: 可以有两种方式去进行
//        disruptor.handleEventsWith(new Handler1(),new Handler2(),new Handler3());

        // 菱形操作 (一) handler1 handler2 并行 ,最后执行handler3
//        disruptor.handleEventsWith(new Handler1(), new Handler2())
//        .handleEventsWith(new Handler3());
        // 菱形操作 (二)
//        disruptor.handleEventsWith(new Handler1(), new Handler2()).then(new Handler3());

        // 六边形操作
        Handler1 h1 = new Handler1();
        Handler2 h2 = new Handler2();
        Handler3 h3 = new Handler3();
        Handler4 h4 = new Handler4();
        Handler5 h5 = new Handler5();
        disruptor.handleEventsWith(h1, h4);
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2, h5).handleEventsWith(h3);

        RingBuffer<Trade> ringBuffer = disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);

        long begin = System.currentTimeMillis();


        ExecutorService es1 = Executors.newFixedThreadPool(1);
        es1.submit(new TradePushlisher(latch, disruptor));


        latch.await();    //进行向下

        disruptor.shutdown();
        es1.shutdown();

        System.err.println("总耗时: " + (System.currentTimeMillis() - begin));

    }
}


class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("MyThreadFactory");
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (Thread.NORM_PRIORITY != t.getPriority()) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}