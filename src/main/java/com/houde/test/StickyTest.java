package com.houde.test;

import com.houde.eventbus2.EventBus;
import com.houde.eventbus2.Subscribe;
import com.houde.eventbus2.ThreadMode;

/**
 * Created by I on 2017/9/17.
 */
public class StickyTest {
    public StickyTest() {
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void fightMainChange(RankServer.FightEvent event) {
        System.out.println("threadId:" + Thread.currentThread().getId() + " name:" + Thread.currentThread().getName() + ", StickyTest event.f:" + event.f);
    }
}
