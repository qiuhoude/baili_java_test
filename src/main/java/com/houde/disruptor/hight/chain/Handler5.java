package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.util.Util;

/**
 * @author qiukun
 * @create 2019-08-08 11:46
 */
public class Handler5 implements EventHandler<Trade> {

    public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        System.err.println(Thread.currentThread().getId() + " handler 5 : GET PRICE: " + event.getPrice());
        Thread.sleep(1000);
        event.setPrice(event.getPrice() + 3.0);
    }

    public static void main(String[] args) {
        final int buffersize = 1024;
        final int indexMask = buffersize - 1;
        final int indexShift = Util.log2(buffersize);

        int sequence = 2048;

        int index = sequence & indexMask;
        int flag = sequence >>> indexShift;

        System.out.println("index:" + index + ", flag:" + flag);
    }
}

