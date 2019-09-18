package com.houde.threadgroup;

/**
 * Created by I on 2018/1/2.
 */
public class JoinTest {

    static class CustomThread1 extends Thread {
        private static int aa = 14;
        static {
            System.out.println(" CustomThread1 aa="+aa);
            aa=12;
            System.out.println("after CustomThread1 aa="+aa);
        }
        public CustomThread1() {
            super("[CustomThread1] Thread");
        }

        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start.");
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println(threadName + " loop at " + i);
                    Thread.sleep(1000);
                }
                System.out.println(threadName + " end.");
            } catch (Exception e) {
                System.out.println("Exception from " + threadName + ".run");
            }
        }
    }

    static class CustomThread extends Thread {
        private static int aa = 15;
        static {
            System.out.println(" CustomThread aa="+aa);
            aa=16;
            System.out.println("after CustomThread aa="+aa);
        }
        CustomThread1 t1;

        public CustomThread(CustomThread1 t1) {
            super("[CustomThread] Thread");
            this.t1 = t1;
        }
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start.");
            try {
                t1.join();
                System.out.println(threadName + " end.");
            } catch (Exception e) {
                System.out.println("Exception from " + threadName + ".run");
            }
        }
    }

    public static void main(String[] args) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        CustomThread1 t1 = new CustomThread1();
        CustomThread t = new CustomThread(t1);
        try {
            t1.start();
            Thread.sleep(2000);
            t.start();
            t.join();//此处注释
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(threadName + " end!");
    }

}

