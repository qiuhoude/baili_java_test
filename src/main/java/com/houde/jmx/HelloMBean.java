package com.houde.jmx;

/**
 * 标准MBean
 * Created by I on 2018/10/8.
 */
public interface HelloMBean {
    public void sayHello();

    public int add(int x, int y);

    public String getName();

    public int getCacheSize();

    public void setCacheSize(int size);
}
