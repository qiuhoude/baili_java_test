package com.houde.threadgroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by I on 2018/1/19.
 */
public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    public static void main(String[] args) {
        FairAndUnfairTest test = new FairAndUnfairTest();
         test.testLock(unfairLock);
//        GraphTest.testLock(fairLock);
    }

    private void testLock(Lock lock) {
        // 启动5个Job
        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock, String.valueOf(i));
            job.start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock, String name) {
            super(name);
            this.lock = lock;
        }

        public void run() {
            // 连续2次打印当前的Thread和等待队列中的Thread
            lock.lock();
            try {
                System.out.print("current thread name is: " + this.getName());
                print_list(lock);
            } finally {
                lock.unlock();
            }
            lock.lock();
            try {
                System.out.print("current thread name is: " + this.getName());
                print_list(lock);
            } finally {
                lock.unlock();
            }

        }

        private void print_list(Lock lock) {
            ReentrantLock2 t_lock = (ReentrantLock2) lock;
            Collection<Thread> threads = t_lock.getQueuedThreads();
            System.out.print(" the thread in queue is : ");
            for (Thread t : threads) {
                System.out.print(t.getName() + ' ');
            }
            System.out.print('\n');
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
