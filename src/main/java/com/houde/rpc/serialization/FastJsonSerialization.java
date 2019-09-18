package com.houde.rpc.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;

/**
 * @author qiukun
 * @create 2019-03-14 16:16
 */
public class FastJsonSerialization implements Serialization {
    static final String charsetName = "UTF-8";

    @Override
    public byte[] serialize(Object data) throws IOException {
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);//<1>
        serializer.config(SerializerFeature.WriteClassName, true);//<1>
        serializer.write(data);
        return out.toBytes(charsetName);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return JSON.parseObject(new String(data, charsetName), clz);
    }
}
