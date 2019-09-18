package com.houde.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用也称为幽灵引用或者幻影引用，它是最弱的一种引用关系。一个持有虚引用的对象，和没有引用几乎是一样的，随时都有可能被垃圾回收器回收。
 * 虚引用必须和引用队列一起使用，它的作用在于跟踪垃圾回收过程。
 * 当phantomReference被放入队列时，说明referent的finalize()方法已经调用，并且垃圾收集器准备回收它的内存了。
 **/
public class PhantomReferenceDemo {
    private static ReferenceQueue<MyObject> queue = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {
        MyObject object = new MyObject();
        Reference<MyObject> phanRef = new PhantomReference<>(object, queue);
        System.out.println("创建的虚拟引用为 : " + phanRef);
        new Thread(new CheckRefQueue()).start();

        object = null;

        int i = 1;
        while (true) {
            System.out.println("第" + i++ + "次GC");
            System.gc();
            TimeUnit.SECONDS.sleep(1);
        }

        /**
         * ====================== 控制台打印 ======================
         * 创建的虚拟引用为 : java.lang.ref.PhantomReference@1d44bcfa
         * 第1次GC
         * MyObject's finalize called
         * 第2次GC
         * 删除的虚引用为: java.lang.ref.PhantomReference@1d44bcfa , 获取虚引用的对象 : null
         * ====================== 控制台打印 ======================
         *
         * 再经过一次GC之后，系统找到了垃圾对象，并调用finalize()方法回收内存，但没有立即加入PhantomReference Queue中。因为MyObject对象重写了finalize()方法，并且该方法是一个非空实现，所以这里MyObject也是一个Final Reference。所以第一次GC完成的是Final Reference的事情。
         * 第二次GC时，该对象(即，MyObject)对象会真正被垃圾回收器进行回收，此时，将PhantomReference加入虚引用队列( PhantomReference Queue )。
         * 而且每次gc之间需要停顿一些时间，已给JVM足够的处理时间；如果这里没有TimeUnit.SECONDS.sleep(1); 可能需要gc到第5、6次才会成功。
         */

    }

    public static class MyObject {

        @Override
        protected void finalize() throws Throwable {
            System.out.println("MyObject's finalize called");
            super.finalize();
        }

        @Override
        public String toString() {
            return "I am MyObject";
        }
    }

    public static class CheckRefQueue implements Runnable {

        Reference<MyObject> obj = null;

        @Override
        public void run() {
            try {
                obj = (Reference<MyObject>) queue.remove();
                System.out.println("删除的虚引用为: " + obj + " , 获取虚引用的对象 : " + obj.get());
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
