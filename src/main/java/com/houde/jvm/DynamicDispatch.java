package com.houde.jvm;

/**
 * 静态分配,设计模式中的外观模式 就是使用这一点
 *
 * @author qiukun
 * @create 2018-06-27 18:53
 **/
public class DynamicDispatch {


    public static abstract class AbsActor {
        public abstract void act();

    }

    public static class YongActor extends AbsActor {
        public void act() {
            System.out.println("yong kongfu act");
        }
    }

    public static class OldActor extends AbsActor {
        public void act() {
            System.out.println("old kongfu act");
        }
    }

    public static void main(String[] args) {
        AbsActor oldActor = new OldActor();
        AbsActor yongActor = new YongActor();
        oldActor.act();
        yongActor.act();

    }


}
