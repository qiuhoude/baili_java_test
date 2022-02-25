package com.houde.rx.creation;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

/**
 * @author qiukun
 * @date 2021/4/29
 */
public class SelfSchedulers extends Scheduler {
    @Override
    public Worker createWorker() {

        return null;
    }

    static final class SelfWork extends Scheduler.Worker{

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
