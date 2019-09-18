package com.houde.test;

import com.houde.eventbus2.EventBus;

/**
 * Created by I on 2017/9/17.
 */
public class TestEventBus {


    public static void main(String[] args) throws InterruptedException {
        RankServer rankServer = new RankServer();
//        System.out.println("创建出server对象，接下来sleep 1s");
//        Thread.sleep(1000);
//        EventBus.getDefault().post(new RankServer.FightEvent(1));
//        EventBus.getDefault().postSticky(new RankServer.FightEvent(100));
        EventBus.getDefault().post(new RankServer.FightEventA(3));
        EventBus.getDefault().post(new RankServer.FightEventA(6));
        EventBus.getDefault().post(new RankServer.FightEventB(2));
        EventBus.getDefault().post(new RankServer.FightEventA(8));
        EventBus.getDefault().post(new RankServer.FightEventB(4));
//        new StickyTest();
        System.out.println("===========结束============");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(new RankServer.FightEventB(8));
        }).start();
//        int kill = 16242;
//        int lost = 11620;
//        int count = (int) (kill * 0.8f + lost * 0.2f);
//        System.out.println(count);
//        double eff = 1000 / 10000.0;
//        count += count * eff;
//        System.out.println(count);
    }

}
