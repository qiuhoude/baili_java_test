package com.houde.rpc.transport;

import com.houde.rpc.serialization.Hessian2Serialization;
import com.houde.rpc.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author qiukun
 * @create 2019-03-14 18:44
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    Serialization serialization = new Hessian2Serialization();

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (genericClass.isInstance(msg)) {
            byte[] data = serialization.serialize(msg);
            out.writeInt(data.length);// 写数据长度
            out.writeBytes(data);
        }
    }
}
