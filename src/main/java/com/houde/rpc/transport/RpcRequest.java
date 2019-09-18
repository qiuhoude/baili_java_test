package com.houde.rpc.transport;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author qiukun
 * @create 2019-03-14 17:51
 */
@Data
public class RpcRequest implements Serializable {
    private long requestId;
    private String interfaceName;
    private String methodName;
    private String parametersDesc;
    private Object[] arguments;
    private Map<String, String> attachments;
    private int retries = 0;
    private byte rpcProtocolVersion;
}
