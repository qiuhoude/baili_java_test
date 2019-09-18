package com.houde.netty.udp;

import com.houde.threadgroup.SleepUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by I on 2018/1/6.
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {
        // 初始化本地UDP的Sock
        LocalUDPSocketProvider.getInstance().initSocket();

        // 启动本地UDP监听（接收数据用的）
        LocalUDPDataReciever.getInstance().startup();

        // 循环发送数据给服务端

        while (true) {
            // 要发送的数据
            String toServer = "Hi，我是客户端，我的时间戳" + System.currentTimeMillis();
            byte[] soServerBytes = toServer.getBytes("UTF-8");

            // 开始发送
//            boolean ok = UDPUtils.send(soServerBytes, soServerBytes.length);
//            if (ok)
//                System.out.println("发往服务端的信息已送出.");
//            else
//                System.out.println("发往服务端的信息没有成功发出！！！");

            // 3000秒后进入下一次循环
            SleepUtils.second(5);

        }
    }

    static class UDPUtils {
        public static boolean send(byte[] soServerBytes, int len) {
            try {

                DatagramSocket ds = LocalUDPSocketProvider.getInstance().getLocalUDPSocket();

                // byte[] data = "hello world".getBytes();
                DatagramPacket dp = new DatagramPacket(soServerBytes, len, InetAddress.getByName("192.168.1.151"),
                        9112);
                // 3、通过scoket服务，将已有的数据发送出去，通过send方法
                ds.send(dp);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
    }

    static class LocalUDPSocketProvider {

        private static volatile LocalUDPSocketProvider instance = null;

        private DatagramSocket localUDPSocket = null;

        public static LocalUDPSocketProvider getInstance() {
            if (instance == null) {
                synchronized (LocalUDPSocketProvider.class) {
                    if (instance == null) {
                        instance = new LocalUDPSocketProvider();
                    }
                }
            }
            return instance;
        }

        public void initSocket() {
            try {
                // UDP本地监听端口（如果为0将表示由系统分配，否则使用指定端口）
                this.localUDPSocket = new DatagramSocket(9998);
                // 调用connect之后，每次send时DatagramPacket就不需要设计目标主机的ip和port了
                // 注意：connect方法一定要在DatagramSocket.receive()方法之前调用，
                // 不然整send数据将会被错误地阻塞。这或许是官方API的bug，也或许是调
                // 用规范就应该这样，但没有找到官方明确的说明
                InetSocketAddress address = new InetSocketAddress("192.168.1.151", 9112);
                this.localUDPSocket.connect(address);
                this.localUDPSocket.setReuseAddress(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public DatagramSocket getLocalUDPSocket() {
            return this.localUDPSocket;
        }

    }
}
