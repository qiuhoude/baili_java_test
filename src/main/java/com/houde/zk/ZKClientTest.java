package com.houde.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qiukun
 * @create 2018-09-29 15:49
 **/
public class ZKClientTest {
    public static void main(String[] args) throws InterruptedException {


        MyZooKeeper mzk = new MyZooKeeper();
        mzk.connect("192.168.1.151:2181");
        TimeUnit.SECONDS.sleep(3);
        ZKOperate zko = new ZKOperate();
        try {
            String s = MyZooKeeper.zooKeeper.create("/GraphTest", "haha".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("main: s= " + s);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        boolean aaa = zko.deteleZNode("/aaa");
//        System.out.println("删除aaa "+aaa);
        List<String> child = zko.getChild("/");

        if (child != null) {
            child.forEach(System.out::println);
        }
        try {

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
