package com.houde.rx.creation;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by I on 2017/12/27.
 */
public class Create {

    public static void main(String[] args) throws InterruptedException {
//        Map<Integer, String> map = new HashMap<>();
//
//        new Thread(() -> {
//            for (int i = 0; i < 100000; i++) {
//                map.put(i,i+":");
//            }
//        }).start();
//
//        new Thread(() -> {
//            for (int i = 100000; i < 900000; i++) {
//                map.put(i,i+":");
//            }
//        }).start();
//
//        new Thread(() -> {
//            for (int i = 900000; i < 10000000; i++) {
//                map.put(i,i+":");
//            }
//        }).start();
//        new Thread(() -> {
//            for (int i = 10000000; i < 100000001; i++) {
//                map.put(i,i+":");
//            }
//        }).start();
        System.out.println(Thread.currentThread() + " begin");
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            System.out.println(Thread.currentThread() + " create");
            e.onComplete();
        })
                .startWith(3)
                .repeat(1)
                .map(it -> {
                    System.out.println(Thread.currentThread() + " map");
                    return it + "";
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single()) //android main

                .subscribe(i -> {

                    System.out.println(Thread.currentThread() + " subscribe onNext : i= " + i);
                });

     /*   MyThread myThread = new MyThread();
        myThread.start();
        System.out.println(Thread.currentThread() + " main before");

        for (int i = 0; i < 10; i++) {
            myThread.addCmd(() -> {
                System.out.println(Thread.currentThread() + " addCmd");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        myThread.addCmd(MyThread.stopCmd);
        System.out.println(Thread.currentThread() + " main after");*/

        Worker worker = Schedulers.newThread().createWorker();



        try {
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("结束!~!!!!!!");
    }


    interface ICommand {
        void action();
    }


    static final class MyThread extends Thread {
        public MyThread() {
            super("MyThread");
        }

        final static ICommand stopCmd = () -> {
            System.out.println(Thread.currentThread() + " stop cmd");
        };
        final BlockingQueue<ICommand> cmdQue = new LinkedBlockingQueue<ICommand>();

        @Override
        public void run() {
            while (true) {
                try {
                    ICommand command = cmdQue.take();
                    command.action();
                    if (command == stopCmd) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void addCmd(ICommand cmd) {
            if (cmd != null) {
                cmdQue.offer(cmd);
            }
        }
    }


    static final class MyWork extends Scheduler.Worker {

        @Override
        public Disposable schedule(Runnable run, long delay, TimeUnit unit) {
            return null;
        }

        @Override
        public void dispose() {

        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }
}
