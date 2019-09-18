package com.houde.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;

/**
 * Created by I on 2018/4/2.
 */
public class MulticastSender {

    public static void main(String[] args) {
        try {
            MulticastSocket socket = new MulticastSocket();
            InetAddress address = InetAddress.getByName("239.0.0.255");// 组播地址
            for (int i = 0; i < 10; i++) {
                String data = "hello client" + i;
                byte[] bytes = data.getBytes();
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9999);
                socket.send(packet);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
