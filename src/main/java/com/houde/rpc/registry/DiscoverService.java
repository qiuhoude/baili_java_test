package com.houde.rpc.registry;

import java.util.List;

public interface DiscoverService {
    //1. 订阅服务
    void subscribe(URL url, NotifyListener listener);

    //2. 取消订阅
    void unsubscribe(URL url, NotifyListener listener);

    //3. 发现服务列表
    List<URL> discover(URL url);
}
