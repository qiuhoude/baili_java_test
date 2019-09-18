package com.houde.zk.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.*;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.concurrent.TimeUnit;

/**
 * @author qiukun
 * @create 2019-05-10 14:50
 */
public class AppServer {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .newClient("192.168.1.151:2181,192.168.1.151:2182,192.168.1.151:2183", new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.blockUntilConnected();

        /**
         * 指定服务的 地址，端口，名称
         */
        ServiceInstanceBuilder<ServiceDetail> sib = ServiceInstance.builder();
        sib.address("192.168.1.100");
        sib.port(8855);
        sib.name("tomcat").uriSpec(new UriSpec("{scheme}://foo.com:{port}"));
        sib.payload(new ServiceDetail("主站web程序", 1));

        ServiceInstance<ServiceDetail> instance = sib.build();

        ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                .client(client)
                .serializer(new JsonInstanceSerializer<>(ServiceDetail.class))
                .basePath(ServiceDetail.REGISTER_ROOT_PATH)
                .build();
        //服务注册
        serviceDiscovery.registerService(instance);
        serviceDiscovery.start();

        TimeUnit.SECONDS.sleep(700);

        serviceDiscovery.close();
        client.close();
    }
}
