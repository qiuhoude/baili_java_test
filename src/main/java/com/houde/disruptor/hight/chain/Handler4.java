package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventHandler;

/**
 * @author qiukun
 * @create 2019-08-08 11:45
 */
public class Handler4 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println(Thread.currentThread().getId()+" handler 4 : SET PRICE");
        Thread.sleep(1000);
        event.setPrice(17.0);
    }

}

