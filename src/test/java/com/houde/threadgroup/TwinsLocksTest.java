package com.houde.threadgroup;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * Created by I on 2018/1/19.
 */
public class TwinsLocksTest {
    @Test
    public void test() {
        final Lock lock = new TwinsLocks();
//        final Lock lock = new Mutex();
        class Worker extends Thread {
            public Worker(String name) {
                super(name);
            }

            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepUtils.second(1);
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker("t"+i);
//            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            SleepUtils.second(1);
            System.out.println();
        }
    }
}