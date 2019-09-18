package com.houde.rpc.proxy;

/**
 * @author qiukun
 * @create 2019-03-14 16:50
 */
public class Test {
    public static void main(String[] args) throws Exception {
        BookApi api = () -> {
            System.out.println("book api invoke");
        };
//        BookApi proxy = CglibProxy.createCglibDynamicProxy(api);
        BookApi proxy = JavassistProxy.createJavassistBytecodeDynamicProxy();
        proxy.sell();
    }
}
