package com.houde.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by I on 2017/12/1.
 */
public class FixedLengthFrameDecoderTest {

    @Test    //1
    public void testFramesDecoded() {
        ByteBuf buf = Unpooled.buffer(); //新建 ByteBuf 并用字节填充它
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3)); //新增 EmbeddedChannel 并添加 FixedLengthFrameDecoder 用于测试
        Assert.assertFalse(channel.writeInbound(input.readBytes(2))); //写数据到 EmbeddedChannel
//        Assert.assertTrue(channel.writeInbound(inpServerBootstraput.readBytes(7)));

        Assert.assertTrue(channel.finish());  //标记 channel 已经完成

        ByteBuf read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        Assert.assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded2() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1
//        ByteBuf sliced = buf.readSlice(14);
//        ByteBuf sliced = buf.slice(0, 14);          //2
//        System.out.println(sliced.toString(utf8));  //3

//        System.out.println(c);
        System.out.println("readerIndex:" + buf.readerIndex());
        System.out.println("readerIndex:" + buf.readerIndex());
        System.out.println("writerIndex:" + buf.writerIndex());
        System.out.println("capacity:" + buf.capacity());
        System.out.println("readableBytes:" + buf.readableBytes());
        System.out.println("writableBytes:" + buf.writableBytes());


        buf.setByte(0, (byte) 'A');                 //4
//        assert buf.getByte(0) == sliced.getByte(0);

    }

    @Test
    public void bufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);     //1

        ByteBuf copy = buf.copy(0, 14);               //2
        System.out.println(copy.toString(utf8));      //3

        buf.setByte(0, (byte) 'J');                   //4

        System.out.println(buf.getByte(0));
        System.out.println(copy.getByte(0));
        assert buf.getByte(0) != copy.getByte(0);
    }
}