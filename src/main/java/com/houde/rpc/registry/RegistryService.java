package com.houde.rpc.registry;

import java.util.Collection;

/**
 * @author qiukun
 * @create 2019-03-19 14:59
 */
public interface RegistryService {
    //1. 向注册中心注册服务
    void register(URL url);

    //2. 从注册中心摘除服务
    void unregister(URL url);

    //3. 将服务设置为可用，供客户端调用
    void available(URL url);

    //4. 禁用服务，客户端无法发现该服务
    void unavailable(URL url);

    //5. 获取已注册服务的集合
    Collection<URL> getRegisteredServiceUrls();

}
