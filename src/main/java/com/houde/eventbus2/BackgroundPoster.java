package com.houde.eventbus2;

/**
 * Created by I on 2017/9/5.
 */
public final class BackgroundPoster implements Runnable {

    private final PendingPostQueue queue;
    private volatile boolean executorRunning;

    BackgroundPoster() {
        queue = new PendingPostQueue();
    }

    public void enqueue(Subscription subscription, Object event) {
        PendingPost pendingPost = PendingPost.obtainPendingPost(subscription, event);
        synchronized (this) {
            queue.enqueue(pendingPost);
            if (!executorRunning) {
                executorRunning = true;
                InvokeHelper.getDefault().getExecutorService().execute(this);
            }
        }
    }

    @Override
    public void run() {
        try {
            try {
                while (true) {
                    PendingPost pendingPost = queue.poll(1000);
                    if (pendingPost == null) {
                        synchronized (this) {
                            // Check again, this time in synchronized
                            pendingPost = queue.poll();
                            if (pendingPost == null) {
                                executorRunning = false;
                                return;
                            }
                        }
                    }
                    InvokeHelper.getDefault().invokeSubscriber(pendingPost);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interruppted " + e);
            }
        } finally {
            executorRunning = false;
        }
    }
}
