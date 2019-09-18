package com.houde.test;

import com.houde.eventbus2.EventBus;
import com.houde.eventbus2.Subscribe;
import com.houde.eventbus2.ThreadMode;

/**
 * Created by I on 2017/9/17.
 */
public class RankServer {

    public RankServer() {
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void fightMainChange(FightEvent event) {
        System.out.println("threadId:" + Thread.currentThread().getId() + " name:" + Thread.currentThread().getName() + ",POSTING event.f:" + event.f);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void fightBChange(FightEventB event) {
        System.out.println("threadId:" + Thread.currentThread().getId() + ", name:" + Thread.currentThread().getName() + ",BACKGROUND event.f:" + event.f);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void fightAChange(FightEventA event) {
        System.out.println("threadId:" + Thread.currentThread().getId() + ", name:" + Thread.currentThread().getName() + ",ASYNC event.f:" + event.f);
    }

    public void destory(){
        System.out.println("destory");
        EventBus.getDefault().unRegister(this);
    }

    public static class FightEvent {
        public int f;

        public FightEvent(int f) {
            this.f = f;
        }
    }

    public static class FightEventB {
        public int f;

        public FightEventB(int f) {
            this.f = f;
        }
    }
    public static class FightEventA {
        public int f;

        public FightEventA(int f) {
            this.f = f;
        }
    }
}
