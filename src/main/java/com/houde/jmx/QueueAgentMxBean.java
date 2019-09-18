package com.houde.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 在MBean Server中创建并注册MXBean
 *
 * @author qiukun
 * @create 2018-10-08 11:30
 **/
public class QueueAgentMxBean {
    public static void main(String[] args) throws  Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName mxbeanName  = new ObjectName("com.houde.jmx:type=QueueSampler");

        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        QueueSampler mxbean = new QueueSampler(queue);

        mbs.registerMBean(mxbean,mxbeanName);
        System.out.println("Waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
