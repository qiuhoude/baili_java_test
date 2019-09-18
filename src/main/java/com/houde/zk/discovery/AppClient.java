package com.houde.zk.discovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * @author qiukun
 * @create 2019-05-10 14:52
 */
public class AppClient {
    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.1.151:2181,192.168.1.151:2182,192.168.1.151:2183",
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        client.blockUntilConnected();

        ServiceDiscovery<ServiceDetail> serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
                .client(client)
                .basePath(ServiceDetail.REGISTER_ROOT_PATH)
                .build();
        serviceDiscovery.start();


        //根据名称获取服务
        Collection<ServiceInstance<ServiceDetail>> services = serviceDiscovery.queryForInstances("tomcat");
        for (ServiceInstance<ServiceDetail> service : services) {
            System.out.println(service.getPayload());
            System.out.println(service.getAddress() + "\t" + service.getPort());
            System.out.println("---------------------");
        }


        serviceDiscovery.close();

        CloseableUtils.closeQuietly(client);
    }
}
