package com.houde.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by I on 2018/2/27.
 */
public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() {
        ServerBootstrap boot = new ServerBootstrap();
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        boot.group(parentGroup, childGroup);
        // boot.localAddress(port);
        boot.channel(NioServerSocketChannel.class); // 选择
        boot.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeLine = channel.pipeline();
                // 心跳时间
                pipeLine.addLast(new IdleStateHandler(360, 0, 0, TimeUnit.SECONDS));
                pipeLine.addLast(new ChannelDuplexHandler() {
                    @Override
                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                        super.userEventTriggered(ctx, evt);
                        if (evt instanceof IdleStateEvent) {
                            IdleStateEvent e = (IdleStateEvent) evt;
                            if (e.state() == IdleState.READER_IDLE) {
                                ctx.close();
                            }
                        }
                    }
                });
                // protoBuf解析 ...
                pipeLine.addLast(new SimpleChannelInboundHandler<Byte>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Byte msg) throws Exception {
                        // 处理自己的逻辑
                    }
                });

            }
        });
        boot.childOption(ChannelOption.SO_KEEPALIVE, true);
        boot.option(ChannelOption.SO_KEEPALIVE, true);
        boot.option(ChannelOption.SO_BACKLOG, 128);
        try {
            // 绑定端口，同步等待成功
            ChannelFuture f = boot.bind(port).sync();
            // 等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
