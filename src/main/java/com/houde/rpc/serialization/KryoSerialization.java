package com.houde.rpc.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author qiukun
 * @create 2019-03-14 15:58
 */
public class KryoSerialization implements Serialization {
    @Override
    public byte[] serialize(Object obj) throws IOException {
        Kryo kryo = kryoLocal.get();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new Output(byteArrayOutputStream)) {//<1>
            kryo.writeClassAndObject(output, obj);//<2>
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        Kryo kryo = kryoLocal.get();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try (Input input = new Input(byteArrayInputStream)) {// <1>
            return (T) kryo.readClassAndObject(input);//<2>
        }
    }

    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            kryo.setRegistrationRequired(false);
            return kryo;
        }
    };
}
