package com.houde.jvm;

/**
 * 静态分配,设计模式中的外观模式 就是使用这一点
 *
 * @author qiukun
 * @create 2018-06-27 18:53
 **/
public class StaticDispatch {
    public interface Role {
    }

    public static class KufuRole implements Role {
    }

    public static class IdioRole implements Role {
    }

    public static abstract class AbsActor {
        public void act(Role role) {
            System.out.println("role act");
        }

        public void act(KufuRole role) {
            System.out.println("kongfu act");
        }

        public void act(IdioRole role) {
            System.out.println("IdioRole act");
        }
    }

    public static class YongActor extends AbsActor {
        public void act(KufuRole role) {
            System.out.println("yong kongfu act");
        }
    }

    public static class OldActor extends AbsActor {
        public void act(KufuRole role) {
            System.out.println("old kongfu act");
        }
    }

    public static void main(String[] args) {
        AbsActor actor = new OldActor();
        Role role = new KufuRole();
        actor.act(role);

    }


}
