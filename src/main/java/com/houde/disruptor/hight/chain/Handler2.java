package com.houde.disruptor.hight.chain;


import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * @author qiukun
 * @create 2019-08-08 11:42
 */
public class Handler2 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println(Thread.currentThread().getId()+" handler 2 : SET ID");
        Thread.sleep(2000);
        event.setId(UUID.randomUUID().toString());
    }


}