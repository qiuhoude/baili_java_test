package com.houde.rpc.baserpc;

/**
 * @author qiukun
 * @create 2019-03-13 18:36
 */
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "Hello " + name;
    }
}
