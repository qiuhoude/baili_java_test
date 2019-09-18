package com.houde.threadgroup;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author qiukun
 * @create 2018-07-05 15:58
 **/
public class ScheduledThreadPoolTest {
    public static void main(String[] args) {
        newSingleThreadScheduledExecutor("lala").scheduleAtFixedRate(() -> {
            System.out.println("thread:"+Thread.currentThread().getId()+" date:"+System.currentTimeMillis());
        }, 1000, 20000, TimeUnit.MILLISECONDS);



    }

    private static ScheduledThreadPoolExecutor newSingleThreadScheduledExecutor(String threadName) {
        ScheduledThreadPoolExecutor exe = new ScheduledThreadPoolExecutor(1, (r) -> {
            return new Thread(r, threadName);
        });
        exe.setMaximumPoolSize(1);
        exe.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        return exe;
    }
}
