package com.houde.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 用来描述非必须的对象，但是它的强度比软引用更弱一些，被弱引用关联的对象只能生存到下一次垃圾收集发送之前。
 * 当垃圾收集器工作时，无论当前内存是否足够，都会回收掉只被弱引用关联的对象。
 * 一旦一个弱引用对象被垃圾回收器回收，便会加入到一个注册引用队列中。
 * @author qiukun
 * @create 2018-09-21 16:22
 **/
public class WeakReferenceDemo {
    private static ReferenceQueue<MyObject> queue = new ReferenceQueue<>();

    public static void main(String[] args) {

        MyObject object = new MyObject();
        Reference<MyObject> weakRef = new WeakReference<>(object, queue);
        System.out.println("创建的弱引用为 : " + weakRef);
        new Thread(new CheckRefQueue()).start();

        object = null;
        System.out.println("Before GC: Weak Get = " + weakRef.get());
        System.gc();
        System.out.println("After GC: Weak Get = " + weakRef.get());

        /**
         * ====================== 控制台打印 ======================
         * 创建的弱引用为 : java.lang.ref.WeakReference@1d44bcfa
         * Before GC: Weak Get = I am MyObject
         * After GC: Weak Get = null
         * MyObject's finalize called
         * 删除的弱引用为 : java.lang.ref.WeakReference@1d44bcfa , 获取到的弱引用的对象为 : null
         * ====================== 控制台打印 ======================
         */
    }

    public static class CheckRefQueue implements Runnable {

        Reference<MyObject> obj = null;

        @Override
        public void run() {
            try {
                obj = (Reference<MyObject>)queue.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(obj != null) {
                System.out.println("删除的弱引用为 : " + obj + " , 获取到的弱引用的对象为 : " + obj.get());

            }

        }
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


}
