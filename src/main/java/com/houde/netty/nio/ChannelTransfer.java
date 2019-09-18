package com.houde.netty.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

/**
 * @author qiukun
 * @create 2019-04-26 10:16
 */
public class ChannelTransfer {
    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream("log.txt");
        FileChannel channel = fis.getChannel();
        channel.transferTo(0, channel.size(), Channels.newChannel(System.out));
        channel.close();
        fis.close();
    }
}
