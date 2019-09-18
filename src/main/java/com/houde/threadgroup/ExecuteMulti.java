package com.houde.threadgroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiukun
 * @create 2019-05-14 10:57
 */
public class ExecuteMulti {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Task task = new Task();
        executorService.execute(task);
        executorService.execute(task);

    }

    static class Task implements Runnable {
        private int cnt;

        @Override
        public void run() {
            System.out.println(cnt + " threadID " + Thread.currentThread().getId());
            cnt++;
        }
    }
}
