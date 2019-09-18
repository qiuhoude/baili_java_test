package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventHandler;

/**
 * @author qiukun
 * @create 2019-08-08 11:46
 */
public class Handler5 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println(Thread.currentThread().getId()+" handler 5 : GET PRICE: " +  event.getPrice());
        Thread.sleep(1000);
        event.setPrice(event.getPrice() + 3.0);
    }

}

