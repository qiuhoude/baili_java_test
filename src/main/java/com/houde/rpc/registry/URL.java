package com.houde.rpc.registry;

import java.util.Map;

/**
 * @author qiukun
 * @create 2019-05-06 20:12
 */
public class URL {
    /*
     // server节点注册的信息
     /motan/demo_group/com.sinosoft.student.api.DemoApi/server/192.168.150.1:6666
     motan://192.168.150.1:6666/com.sinosoft.student.api.DemoApi?serialization=hessian2&protocol=motan&isDefault=true&maxContentLength=1548576&shareChannel=true&refreshTimestamp=1515122649835&id=motanServerBasicConfig&nodeType=service&export=motan:6666&requestTimeout=9000000&accessLog=false&group=demo_group&

    // client 节点
    /motan/demo_group/com.sinosoft.student.api.DemoApi/client/192.168.150.1
    motan://192.168.150.1:0/com.sinosoft.student.api.DemoApi?singleton=true&maxContentLength=1548576&check=false&nodeType=service&version=1.0&throwException=true&accessLog=false&serialization=hessian2&retries=0&protocol=motan&isDefault=true&refreshTimestamp=1515122631758&id=motanClientBasicConfig&requestTimeout=9000&group=demo_group
     */
    private String protocol;//协议名称
    private String host;
    private int port;
    // interfaceName,也代表着路径
    private String path;
    private Map<String, String> parameters;
    private volatile transient Map<String, Number> numbers;
}
