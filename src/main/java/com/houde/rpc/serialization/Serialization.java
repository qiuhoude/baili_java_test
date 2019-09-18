package com.houde.rpc.serialization;

import java.io.IOException;

/**
 * @author qiukun
 * @create 2019-03-14 15:57
 */
public interface Serialization {
    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;
}
