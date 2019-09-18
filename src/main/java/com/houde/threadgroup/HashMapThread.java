package com.houde.threadgroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by I on 2017/8/19.
 */
public class HashMapThread extends Thread {
    private static AtomicInteger ai = new AtomicInteger(0);
    //    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>(1);
    private static Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public void run() {
        int tmp = 0;
        while ((tmp = ai.getAndIncrement()) < 1000000) {
            map.put(tmp, tmp);
        }
    }

    public static void main(String[] args) {

        HashMapThread hmt0 = new HashMapThread();
        HashMapThread hmt1 = new HashMapThread();
        HashMapThread hmt2 = new HashMapThread();
        HashMapThread hmt3 = new HashMapThread();
        HashMapThread hmt4 = new HashMapThread();
        hmt0.start();
        hmt1.start();
        hmt2.start();
        hmt3.start();
        hmt4.start();
        try {
//            Thread.sleep(2000);
//            final int cnt = ai.get();
//            System.out.println("cnt :" + cnt);
//            for (int i = 0; i < cnt; i++) {
//                Integer val = map.get(i);
//                System.out.println("key: " + i + ", val: " + val);
//            }

            hmt0.join();
            hmt1.join();
            hmt2.join();
            hmt3.join();
            hmt4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("添加的个数 ：" + ai.get() + " mapSize:" + map.size());

    }
}
