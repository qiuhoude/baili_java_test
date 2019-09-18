package com.houde.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by I on 2017/12/2.
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
    //继承 MessageToMessageEncoder 用于编码消息到另外一种格式
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        while (msg.readableBytes() >= 4) {//检查是否有足够的字节用于编码
            int value = Math.abs(msg.readInt());//读取下一个输入 ByteBuf 产出的 int 值，并计算绝对值
            out.add(value);//写 int 到编码的消息 List
        }

    }
}
