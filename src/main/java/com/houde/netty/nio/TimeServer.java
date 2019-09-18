package com.houde.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * server端
 *
 * @author qiukun
 * @create 2018-04-24 15:33
 **/
public class TimeServer {
    private static ExecutorService executor;
    static {
        executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000));
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel ssc= ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(8080));
        while (true){
            SocketChannel socketChannel = ssc.accept();
            if(socketChannel==null){
                continue;
            }else{
                socketChannel.configureBlocking(false);
                executor.submit(new TimeServerHandleTask(socketChannel,executor));
            }
        }
    }
}
