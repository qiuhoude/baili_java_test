package com.houde.threadgroup;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by I on 2018/1/31.
 */
public class ForkJoinCountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2; // 阈值
    private int start;
    private int end;

    public ForkJoinCountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            ForkJoinCountTask lefTask = new ForkJoinCountTask(start, middle);
            ForkJoinCountTask rightTask = new ForkJoinCountTask(middle, end);
            // 执行子任务
            lefTask.fork();
            rightTask.fork();
            // 等待子任务完成
            int leftResult = lefTask.join();
            int rightResult = rightTask.join();
            sum = leftResult + rightResult;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 生产计算任务 1+..4
        ForkJoinCountTask task = new ForkJoinCountTask(1, 4);
        // 执行一个任务
        ForkJoinTask<Integer> result = forkJoinPool.submit(task);

//        if (task.isCompletedAbnormally()){
//            System.out.println(task.getException());
//        }
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
