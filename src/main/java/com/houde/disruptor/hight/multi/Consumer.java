package com.houde.disruptor.hight.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiukun
 * @create 2019-08-08 16:05
 */
public class Consumer implements WorkHandler<OrderEvent> {
    private String comsumerId;

    private static AtomicInteger count = new AtomicInteger(0);

    private Random random = new Random();

    public Consumer(String comsumerId) {
        this.comsumerId = comsumerId;
    }

    @Override
    public void onEvent(OrderEvent event) throws Exception {
        Thread.sleep(1 * random.nextInt(5));
        System.err.println(Thread.currentThread().getName()+" 当前消费者: " + this.comsumerId + ", 消费信息ID: " + event.getId());
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
