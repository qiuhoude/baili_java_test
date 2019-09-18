package com.houde.jmx;

import java.util.Date;
import java.util.Queue;

/**
 * 定义MXBean操作
 * @author qiukun
 * @create 2018-10-08 11:27
 **/
public class QueueSampler implements QueueSamplerMXBean {
    private Queue<String> queue;

    public QueueSampler(Queue<String> queue) {
        this.queue = queue;
    }

    public QueueSample getQueueSample() {
        synchronized (queue) {
            return new QueueSample(new Date(),
                    queue.size(), queue.peek());
        }
    }

    public void clearQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
