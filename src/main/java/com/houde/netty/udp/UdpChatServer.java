package com.houde.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by I on 2018/1/4.
 */
public class UdpChatServer {
    // UDP服务监听的数据通道
    public static Channel channel;

    public static ChannelHandlerContext ctx;

    // 搞个map保存与客户端地址的映射关系
//    public static ConcurrentMap<Integer, UdpClient> userSocketMap = new ConcurrentHashMap<Integer, UdpClient>();

    // 创建一个阻塞队列，用于消息缓冲
    public static BlockingQueue<DatagramPacket> msgQueue = new LinkedBlockingQueue<DatagramPacket>();

    private int port;// 监听端口号

    public UdpChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();//udp不能使用ServerBootstrap
            b.group(workerGroup).channel(NioDatagramChannel.class)//设置UDP通道
                    .handler(new UdpServerInitializer())//初始化处理器
                    .option(ChannelOption.SO_BROADCAST, true)// 支持广播
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)// 设置UDP读缓冲区为1M
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024);// 设置UDP写缓冲区为1M

            System.out.println("[UDP 启动了]");

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            channel = f.channel();

            // 等待服务器 socket 关闭 。
            // 这不会发生，可以优雅地关闭服务器。
            f.channel().closeFuture().await();

        } finally {
            workerGroup.shutdownGracefully();

            System.out.println("[UDP 关闭了]");
        }
    }

}

/**
 * netty处理器配置
 *
 * @author kokJuis
 * @version 1.0
 * @date 2016-9-30
 */
  class UdpServerInitializer extends
        ChannelInitializer<NioDatagramChannel> {

    io.netty.util.Timer timer;

    public UdpServerInitializer() {
        timer = new HashedWheelTimer();
    }

    @Override
    public void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /**
         * 从TCP与UDP的区别讲起
         * 网络数据经过路由器,如果数据很小,没有超过路由器的封包大小,就会直接直接经过路由器到达下一个路由器,一层一层最终到达目的地
         * 如果数据很大,这里指一个发送,超过了路由器的封包大小,那么路由器就会把这个数据包进行拆分,比如拆分成A B
         * C三个包,这三个包都没有超过路由器的封包大小,到达下一个路由器的时候,TCP与UDP的区别就来了：
         * TCP收到A的时候,会resp通知源路由器,A到达,B C包依然如此,如果由于网络的各种原因，目的路由收到了A
         * C,B没有收到，TCP会要求源路由把B包重新发一次
         * ，直到ABC包目的路由都接受到了，那么目的路由把ABC包重新组成起始包，继续往下一个路由发送
         * ，这就是TCP安全连接的由来，只要发送，我就能保证目的一定能收到（网络断开能检测到）
         * UDP则不是这样，如果ABC包拆分之后，目的路由只收到AC
         * ，经过检测，B没有被收到，那么此包就会被当作不完整，直接被丢弃。由于UDP没有resp的通知过程
         * ，所以，UDP的传输效率要高一些，当然安全性也低一些
         * 由上面的这些可以得出结论：UDP是绝对不会被粘包，因为路由器收到的只会是完整数据才会继续下发，什么粘包处理完全没有必要
         * 一般网络编程时候，也会定义数据包头，包体 TCP接收数据的时候，可以先接收包头进行安全验证，通过继续接受包体，不通过直接断开连接
         * UDP接受则没有办法这样做
         * ，你再大的一个数据，一个RECV,也是直接接受，不能说我先接受多长，这样是不可能的（不过一般大文件数据，都不会用UDP这种不安全传输）
         */

        // 添加UDP解码器
        // pipeline.addLast("datagramPacketDecoder", new DatagramPacketDecoder(
        // new ProtobufDecoder(Message.getDefaultInstance())));
        // 添加UDP编码器
        // pipeline.addLast("datagramPacketEncoder",
        // new DatagramPacketEncoder<Message>(new ProtobufEncoder()));

//        pipeline.addLast("handler", new UdpChatServerHandler());//消息处理器
//        pipeline.addLast("ackHandler", new UdpAckServerHandler());//ack处理器
//
//        pipeline.addLast("timeout", new IdleStateHandler(180, 0, 0,
//                TimeUnit.SECONDS));// //此两项为添加心跳机制,60秒查看一次在线的客户端channel是否空闲
//        pipeline.addLast(new UdpHeartBeatServerHandler());// 心跳处理handler

    }

}
class UdpChatServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //注意，UDP的通道至始至终只有一个，关了就不能接收了。
        System.out.println("UDP通道已经连接");
        UdpChatServer.ctx = ctx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket
            packet) throws Exception {

        System.out.println("消息来源"  + packet.sender().getHostString() +":"+ packet.sender().getPort());
        // 消息处理。。。。。
        //消息发送。。。。
        DatagramPacket dp = new DatagramPacket(Unpooled.copiedBuffer("消息".getBytes()), packet.sender());
        UdpChatServer.channel.writeAndFlush(dp);

    }



}
