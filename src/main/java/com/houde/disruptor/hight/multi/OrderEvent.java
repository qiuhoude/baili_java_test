package com.houde.disruptor.hight.multi;

import com.lmax.disruptor.EventFactory;

/**
 * @author qiukun
 * @create 2019-08-08 15:50
 */
public class OrderEvent {
    private String id;
    private String name;
    private double price;
    public static final EventFactory<OrderEvent> FACTORY = new EventFactory<OrderEvent>() {
        @Override
        public OrderEvent newInstance() {
            return new OrderEvent();
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
}
