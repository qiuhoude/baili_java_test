package com.houde.threadgroup;



import com.sun.tools.javac.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * @author qiukun
 * @date 2022/1/13
 */
public class ForkJoinHelper {
    private static final Logger log = LoggerFactory.getLogger(ForkJoinHelper.class);


    /**
     * 获取结果
     *
     * @param tasks
     * @param timeout
     * @param <T>
     * @return
     */
    public static <T> Pair<Boolean, List<T>> get(List<? extends ForkJoinTask<T>> tasks, long timeout) {
        List<T> result = new ArrayList<>();
        for (ForkJoinTask<T> task : tasks) {
            long time = System.currentTimeMillis();
            try {
                T t = task.get(timeout, TimeUnit.MILLISECONDS);
                result.add(t);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                // 有一个异常，其他的任务就没有执行的必要了
                for (ForkJoinTask<T> cell : tasks) {
                    cell.cancel(true);
                }
                log.error("excute_forkjoin_task_error", e);
                return Pair.of(false, result);
            } finally {
                time = System.currentTimeMillis() - time;
                timeout = timeout - time <= 0 ? 1 : timeout - time;
            }
        }
        return Pair.of(true, result);
    }

    public static <T> List<ForkJoinTask<T>> submit(List<Supplier<T>> tasks) {
        List<ForkJoinTask<T>> futures = new ArrayList<>();
        for (Supplier<T> task : tasks) {
            ForkJoinTask<T> future = new ForkableTask<T>(task).fork();
            futures.add(future);
        }
        return futures;
    }

    public static List<ForkJoinTask<Void>> execute(List<Runnable> tasks) {
        List<ForkJoinTask<Void>> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            ForkJoinTask<Void> future = new ForkableAction(task).fork();
            futures.add(future);
        }
        return futures;
    }

    public static List<ForkJoinTask<Void>> execute(List<Runnable> tasks, ForkJoinPool pool) {
        List<ForkJoinTask<Void>> futures = new ArrayList<>();
        for (Runnable task : tasks) {
            ForkableAction action = new ForkableAction(task);
            if (Thread.currentThread() instanceof ForkJoinWorkerThread) {
                ForkJoinTask<Void> future = action.fork();
                futures.add(future);
            } else {
                ForkJoinTask<Void> future = pool.submit(action);
                futures.add(future);
            }
        }
        return futures;
    }

    public static class ForkableTask<V> extends RecursiveTask<V> {

        private Supplier<V> supplier;

        public ForkableTask(Supplier<V> supplier) {
            super();
            this.supplier = supplier;
        }

        @Override
        protected V compute() {
            return supplier.get();
        }
    }

    public static class ForkableAction extends RecursiveAction {
        private Runnable runnable;

        public ForkableAction(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        @Override
        protected void compute() {
            runnable.run();
        }
    }
}
