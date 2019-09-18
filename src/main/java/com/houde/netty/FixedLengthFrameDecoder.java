package com.houde.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by I on 2017/12/1.
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {

    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) {
        //指定产出的帧的长度
        if (frameLength <= 0) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("in.readableBytes()"+in.readableBytes());
        if (in.readableBytes() >= frameLength) { //检查是否有足够的字节用于读到下个帧
            ByteBuf buf = in.readBytes(frameLength);//从 ByteBuf 读取新帧
            out.add(buf); //添加帧到解码好的消息 List
        }
    }
}
