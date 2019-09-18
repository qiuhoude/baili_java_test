package com.houde.chain2obsever;

/**
 * Created by I on 2018/3/24.
 */
public class TopDnsServer extends DnsServer {
    @Override
    protected void sign(Recorder recorder) {
        System.out.println("全球顶级DNS服务器 sign");
        recorder.setOwner("全球顶级DNS服务器");
    }

    @Override
    protected boolean isLocal(Recorder recorder) {
        // 所有的域名最终的解析地点
        return true;
    }
}