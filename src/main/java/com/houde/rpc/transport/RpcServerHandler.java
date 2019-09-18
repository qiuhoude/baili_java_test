package com.houde.rpc.transport;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author qiukun
 * @create 2019-03-15 14:03
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest req) throws Exception {
        final RpcResponse res = invoke(req);
        ctx.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
    }


    private RpcResponse invoke(RpcRequest req) {
        RpcResponse res = new RpcResponse();
        res.setRequestId(req.getRequestId());
        return res;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();// 应该是使用日志进行打印
        ctx.close();
    }
}
