package com.houde.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author qiukun
 * @create 2018-09-29 11:50
 **/
public class MyZooKeeper implements Watcher {
    protected CountDownLatch countDownLatch = new CountDownLatch(1);
    //缓存时间
    private static final int SESSION_TIME = 20;
    public static ZooKeeper zooKeeper = null;

    @Override
    public void process(WatchedEvent event) {
        System.out.println("收到事件通知:" + event.getState());
        if (event.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        } else {
            switch (event.getState()) {
                case Expired:
                    // session过期,这是个非常严重的问题,有可能client端出现了问题,也有可能zk环境故障
                    // 此处仅仅是重新实例化zk client
                    System.out.println("过期");
                    break;
                case Disconnected:
                    System.out.println("断开");
                    break;
                case AuthFailed:
                    System.out.println("无权限");
                    close();
                    break;
            }
        }

    }

    public void connect(String host) {
        if (zooKeeper == null) {
            try {
                zooKeeper = new ZooKeeper(host, SESSION_TIME, this);
                countDownLatch.await();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void close() {
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}