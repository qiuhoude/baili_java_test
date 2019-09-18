package com.houde.rpc.registry;


import java.util.List;

/**
 * @author qiukun
 * @create 2019-03-19 15:01
 */
public interface NotifyListener {

    void notify(URL registryUrl, List<URL> urls);
}
