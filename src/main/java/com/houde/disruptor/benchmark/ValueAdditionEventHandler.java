package com.houde.disruptor.benchmark;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.CountDownLatch;

/**
 * @author qiukun
 * @create 2019-07-29 14:19
 */
public class ValueAdditionEventHandler implements EventHandler<ValueEvent> {
    private long value = 0;
    private long count;
    private CountDownLatch latch;

    public long getValue() {
        return value;
    }

    public void reset(final CountDownLatch latch, final long expectedCount) {
        value = 0;
        this.latch = latch;
        count = expectedCount;
    }

    @Override
    public void onEvent(ValueEvent event, long sequence, boolean endOfBatch) throws Exception {
        value = event.getValue();
        if (count == sequence) {
            latch.countDown();
        }
    }
}
