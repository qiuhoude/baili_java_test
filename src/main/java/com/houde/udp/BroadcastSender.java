package com.houde.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by I on 2018/4/2.
 */
public class BroadcastSender {
    public static void main(String[] args) throws Exception {

        InetAddress destination = InetAddress.getByName("255.255.255.255");
        DatagramSocket serverSocket = new DatagramSocket();

        for (int i = 0; i < 10; i++) {

            byte[] buf = ("Hello client"+i).getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, destination, 9999);
            serverSocket.send(packet);
            TimeUnit.SECONDS.sleep(1);
        }
        serverSocket.close();
    }
}
