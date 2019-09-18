package com.houde.rpc.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author qiukun
 * @create 2019-03-15 14:09
 */
public class RpcNettyConsumer {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RpcDecoder(RpcResponse.class));
                            pipeline.addLast(new RpcEncoder(RpcRequest.class));
                            pipeline.addLast(new RpcClientHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true);
            // 连接 RPC 服务器
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8087).sync();
            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();

            RpcRequest rpcRequest = new RpcRequest();
            rpcRequest.setRequestId(123456L);

            channel.writeAndFlush(rpcRequest).sync();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
