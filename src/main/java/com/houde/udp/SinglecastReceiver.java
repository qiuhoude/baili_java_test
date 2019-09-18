package com.houde.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by I on 2018/4/2.
 */
public class SinglecastReceiver {
    public static void main(String[] args) throws Exception {

        DatagramSocket clientSocket = new DatagramSocket(9999);
        byte[] buf = new byte[1024];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            clientSocket.receive(packet);
            System.out.println(new String(packet.getData()).trim());
        }

    }
}
