package com.houde.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qiukun
 * @create 2018-04-25 19:06
 **/
public class TCPServerSelector {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(9999));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        int bufSize = 1024;
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;// nothing to do
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
                    clntChan.configureBlocking(false); // Must be nonblocking to registry
                    // Register the selector with new channel for read and attach byte buffer
                    clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                    //客户端才有

                } else if (key.isReadable()) {
                    // a channel is ready for reading

                    // Client socket channel has pending data
                    SocketChannel clntChan = (SocketChannel) key.channel();
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    long bytesRead = clntChan.read(buf);
                    if (bytesRead == -1) { // Did the other end close?
                        clntChan.close();
                    } else if (bytesRead > 0) {
                        // Indicate via key that reading/writing are both of interest now.
                        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    }
                } else if (key.isWritable() || key.isValid()) {
                    // a channel is ready for writing
                    // Retrieve data read earlier
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    buf.flip(); // Prepare buffer for writing
                    SocketChannel clntChan = (SocketChannel) key.channel();
                    clntChan.write(buf);
                    if (!buf.hasRemaining()) { // Buffer completely written?
                        //Nothing left, so no longer interested in writes
                        key.interestOps(SelectionKey.OP_READ);
                    }
                    buf.compact(); // Make room for more data to be read in

                }
                keyIterator.remove();
            }
        }
    }

}
