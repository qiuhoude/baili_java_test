package com.houde.disruptor;

import sun.misc.Contended;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiukun
 * @create 2019-08-20 15:24
 */
public class FalseSharing implements Runnable {
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private static PaddedAtomic[] longs;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println("Thread num " + i + " duration = " + (System.currentTimeMillis() - start));
        }

    }

    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new PaddedAtomic[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new PaddedAtomic();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].getAndIncrement();
        }
    }

    //    @Contended
    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        //        @Contended 需要加 -XX:-RestrictContended
        protected int p8;
        protected volatile int value = 0;
        protected long p9, p10, p11, p12, p13, p14, p15;

    }

        public final static class ValueNoPadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected int p8;
        protected volatile int value = 0;
        protected long p9, p10, p11, p12, p13, p14, p15;
    }

    @Contended
    public final static class PaddedAtomic extends AtomicInteger {
        private long p1, p2, p3, p4, p5, p6 ;
        private int p8;
//        private long p9, p10, p11, p12, p13, p14, p15;

        public PaddedAtomic() {
        }

        public PaddedAtomic(int initialValue) {
            super(initialValue);
        }
    }

}
