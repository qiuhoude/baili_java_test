package com.houde.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * @author qiukun
 * @create 2018-10-08 11:13
 **/
public class Hello extends NotificationBroadcasterSupport implements HelloMBean {

    private final String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private static final int DEFAULT_CACHE_SIZE = 200;
    private long sequenceNumber = 1;

    public void sayHello() {
        System.out.println("hello, world");
    }

    public int add(int x, int y) {
        return x + y;
    }

    public String getName() {
        return this.name;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public synchronized void setCacheSize(int size) {
        int oldSize = this.cacheSize;
        this.cacheSize = size;
        System.out.println("Cache size now " + this.cacheSize);

        // 构建通知
        Notification n = new AttributeChangeNotification(this,
                sequenceNumber++, System.currentTimeMillis(),
                "CacheSize changed", "CacheSize", "int", oldSize,
                this.cacheSize);
        //发送通知
        sendNotification(n);
    }

    //返回这个MBean将会发送的通知类型信息
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "An attribute of this MBean has changed";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name,
                description);
        return new MBeanNotificationInfo[] { info };
    }
}
