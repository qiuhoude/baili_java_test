package com.houde.rpc.transport;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author qiukun
 * @create 2019-03-14 17:53
 */
@Data
public class RpcResponse implements Serializable {
    private long requestId;
    private Object value; // 返回的值
    private Exception exception;
    private long processTime;
    private int timeout;
    private Map<String, String> attachments;// rpc协议版本兼容时可以回传一些额外的信息
    private byte rpcProtocolVersion;
}
