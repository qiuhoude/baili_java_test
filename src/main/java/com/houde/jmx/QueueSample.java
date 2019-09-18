package com.houde.jmx;

import java.beans.ConstructorProperties;
import java.util.Date;

/**
 * 定义MXBean接口返回的Java类型
 * @author qiukun
 * @create 2018-10-08 11:27
 */
public class QueueSample {
    private final Date date;
    private final int size;
    private final String head;

    @ConstructorProperties({"date", "size", "head"})
    public QueueSample(Date date, int size,
                       String head) {
        this.date = date;
        this.size = size;
        this.head = head;
    }

    public Date getDate() {
        return date;
    }

    public int getSize() {
        return size;
    }

    public String getHead() {
        return head;
    }
}
