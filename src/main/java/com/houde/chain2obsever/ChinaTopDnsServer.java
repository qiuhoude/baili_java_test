package com.houde.chain2obsever;

/**
 * Created by I on 2018/3/24.
 */
public class ChinaTopDnsServer extends DnsServer {
    @Override
    protected void sign(Recorder recorder) {
        System.out.println("中国顶级DNS服务器 sign");
        recorder.setOwner("中国顶级DNS服务器");
    }
    @Override
    protected boolean isLocal(Recorder recorder) {
        return recorder.getDomain().endsWith(".cn");
    }
}
