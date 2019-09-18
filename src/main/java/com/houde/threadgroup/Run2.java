package com.houde.threadgroup;

/**
 * Created by I on 2017/9/8.
 */
public class Run2 {
    static class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                System.out.println("run begin");
                Thread.sleep(2000000);
                System.out.println("run end");
            } catch (InterruptedException e) {
                boolean isInterrupted = this.isInterrupted();
                boolean interrupted = interrupted();
                System.out.println("sleep中被停止  isInterrupte="+isInterrupted+" , interrupted="+interrupted);
                e.printStackTrace();
            }

//            for (int i = 0; i < 500; i++) {
//                if (interrupted()) {
//                    System.out.println("已经停止,要退出");
//                    break;
//                 }
//                System.out.println("i=" + (i + 1));
//            }
//            System.out.println("xianc ===");
        }
    }

    public static void main(String[] args) {
        try {
            MyThread t = new MyThread();
            t.start();
            Thread.sleep(200);
            t.interrupt();
            System.out.println("是否停止1？ " + t.isInterrupted());
            System.out.println("是否停止2？ " + t.isInterrupted());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread.currentThread().interrupt();
//        System.out.println("是否停止1？ " + Thread.interrupted());
//        System.out.println("是否停止2？ " + Thread.interrupted());
        System.out.println("end------");
    }
}
