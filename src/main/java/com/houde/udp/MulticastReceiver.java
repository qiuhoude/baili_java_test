package com.houde.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by I on 2018/4/2.
 */
public class MulticastReceiver {

    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket(9999);
            InetAddress address = InetAddress.getByName("239.0.0.255");// 组播地址
            socket.joinGroup(address);// 加入组播组
            byte[] buf = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.out.println(new String(packet.getData()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
