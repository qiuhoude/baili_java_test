package com.houde.threadgroup;

/**
 * Created by I on 2018/1/3.
 */
public class Daemon {

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                System.out.println("DaemonThread finally run.");//当时daemon线程时,将不会执行finally块中的语句
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }
}
