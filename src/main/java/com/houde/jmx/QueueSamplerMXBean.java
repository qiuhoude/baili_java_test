package com.houde.jmx;

/**
 * MXBean接口
 * Created by I on 2018/10/8.
 */
public interface QueueSamplerMXBean {
    public QueueSample getQueueSample();
    public void clearQueue();
}
