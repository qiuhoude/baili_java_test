package com.houde.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by I on 2017/12/2.
 */
public class AbsIntegerEncoderTest {
    @Test   //1
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();  //新建 ByteBuf 并写入负整数
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        //新建 EmbeddedChannel 并安装 AbsIntegerEncoder 来测试
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        Assert.assertTrue(channel.writeOutbound(buf)); //写 ByteBuf 并预测 readOutbound() 产生的数据

        Assert.assertTrue(channel.finish()); //标记 channel 已经完成
        for (int i = 1; i < 10; i++) {
            Object o = channel.readOutbound();
            System.out.println(o);
            Assert.assertEquals(i, o);  //读取产生到的消息，检查负值已经编码为绝对值
        }
        Assert.assertNull(channel.readOutbound());
    }

}