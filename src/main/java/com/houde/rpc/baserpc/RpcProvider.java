package com.houde.rpc.baserpc;

/**
 * @author qiukun
 * @create 2019-03-13 18:37
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }

}
