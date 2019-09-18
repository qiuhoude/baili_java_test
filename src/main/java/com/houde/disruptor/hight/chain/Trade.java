package com.houde.disruptor.hight.chain;

import com.lmax.disruptor.EventFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qiukun
 * @create 2019-08-06 20:00
 */
public class Trade   {

    private String id;
    private String name;
    private double price;
    private AtomicInteger count = new AtomicInteger();

    public static final EventFactory<Trade> FACTORY = new EventFactory<Trade>() {
        @Override
        public Trade newInstance() {
            return new Trade();
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }
}
