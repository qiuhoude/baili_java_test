package com.houde.chain2obsever;

/**
 * Created by I on 2018/3/24.
 */
public class SHDnsServer extends DnsServer {
    @Override
    protected boolean isLocal(Recorder r) {
        return r.getDomain().endsWith(".sh.cn");
    }

    @Override
    protected void sign(Recorder r) {
        System.out.println("上海DNS服务器 sign");
        r.setOwner("上海DNS服务器");
    }
}
