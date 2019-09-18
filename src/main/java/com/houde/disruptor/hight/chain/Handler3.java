package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventHandler;

/**
 * @author qiukun
 * @create 2019-08-08 11:44
 */
public class Handler3 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println(Thread.currentThread().getId()+" handler 3 : NAME: "
                + event.getName()
                + ", ID: "
                + event.getId()
                + ", PRICE: "
                + event.getPrice()
                + " INSTANCE : " + event.toString());
    }
}
