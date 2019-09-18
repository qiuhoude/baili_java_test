package com.houde.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author qiukun
 * @create 2018-09-29 11:56
 **/
public class ZKOperate {



    /**
     * 节点创建类型(CreateMode)
     * 1、PERSISTENT:持久化节点
     * 2、PERSISTENT_SEQUENTIAL:顺序自动编号持久化节点，这种节点会根据当前已存在的节点数自动加 1
     * 3、EPHEMERAL:临时节点客户端,session超时这类节点就会被自动删除
     * 4、EPHEMERAL_SEQUENTIAL:临时自动编号节点
     */
    public boolean createZNode(String path, String data) {
        try {
            String zkPath = MyZooKeeper.
                    zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("ZooKeeper创建节点成功，节点地址：" + zkPath);
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return false;
    }

    public boolean deteleZNode(String path) {
        try {
            MyZooKeeper.zooKeeper.delete(path, -1);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;

    }


    public boolean updateZNodeData(String path, String data) {
        try {
            Stat stat = MyZooKeeper.zooKeeper.setData(path, data.getBytes(), -1);
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String readData(String path) {
        String data = null;
        try {
            data = new String(MyZooKeeper.zooKeeper.getData(path, false, null));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }


    public List<String> getChild(String path) {
        try {
            List<String> children = MyZooKeeper.zooKeeper.getChildren(path, false);
            return children;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isExists(String path) {
        try {
            Stat stat = MyZooKeeper.zooKeeper.exists(path, false);
            return null != stat;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
