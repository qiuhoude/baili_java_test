package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author qiukun
 * @create 2019-08-08 11:42
 */
public class Handler1 implements EventHandler<Trade>, WorkHandler<Trade> {

    //EventHandler
    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    //WorkHandler
    public void onEvent(Trade event) throws Exception {
        System.err.println(Thread.currentThread().getId()+" handler 1 : SET NAME");
        Thread.sleep(1000);
        event.setName("H1");
    }

}