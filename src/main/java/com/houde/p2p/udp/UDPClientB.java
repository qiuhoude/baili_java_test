package com.houde.p2p.udp;

import java.net.*;

/**
 * @author qiukun
 * @create 2018-06-23 11:29
 **/
public class UDPClientB {

    public static void main(String[] args) {
        try {
            //向server发起请求
            SocketAddress target = new InetSocketAddress("10.1.11.137", 2008);
            DatagramSocket client = new DatagramSocket();
            String message = "I am UDPClientB 192.168.85.129";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            //接收server的回复内容
            byte[] buf = new byte[1024];
            DatagramPacket recpack = new DatagramPacket(buf, buf.length);
            client.receive(recpack);
            //处理server回复的内容，然后向内容中的地址与端口发起请求（打洞）
            String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
            String[] params = receiveMessage.split(",");
            String host = params[0].substring(5);
            String port = params[1].substring(5);
            System.out.println(host + ":" + port);
            sendMessage(host, port, client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向UPDClientA发起请求(在NAT上打孔)
    private static void sendMessage(String host, String port, DatagramSocket client) {
        try {
            SocketAddress target = new InetSocketAddress(host, Integer.parseInt(port));
            for (; ; ) {
                String message = "I am master 192.168.85.129 count GraphTest";
                byte[] sendbuf = message.getBytes();
                DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
                client.send(pack);
                //接收UDPClientA回复的内容
                receive(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //收到UDPClientA的回复内容，穿透已完成
    private static void receive(DatagramSocket client) {
        try {
            for (; ; ) {
                //将接收到的内容打印
                byte[] buf = new byte[1024];
                DatagramPacket recpack = new DatagramPacket(buf, buf.length);
                client.receive(recpack);
                String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
                System.out.println(receiveMessage);

                //记得重新收地址与端口，然后在以新地址发送内容到UPDClientA,就这样互发就可以了。
                int port = recpack.getPort();
                InetAddress address = recpack.getAddress();
                String reportMessage = "I am master 192.168.85.129 count GraphTest";

                //发送消息
                sendMessage(reportMessage, port, address, client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
        try {

            byte[] sendBuf = reportMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            client.send(sendPacket);
            System.out.println("send success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
