package com.houde.chain2obsever;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by I on 2018/3/24.
 */
public abstract class DnsServer extends Observable implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        Recorder r = (Recorder) arg;
        if (isLocal(r)) {
            r.setIp(genIpAddress());
        } else {
            responsFromUpperServer(r);
        }
        sign(r);
    }

    public void setUpperServer(DnsServer server) {
        super.deleteObservers();
        super.addObserver(server);
    }

    // 向父DNS请求解析，也就是通知观察者
    private void responsFromUpperServer(Recorder recorder) {
        super.setChanged();
        super.notifyObservers(recorder);
    }

    // 随机产生一个IP地址，工具类
    private String genIpAddress() {
        Random rand = new Random();
        String address = rand.nextInt(255) + "." + rand.nextInt(255) + "." + rand.nextInt(255) + "."
                + rand.nextInt(255);
        return address;
    }

    // 每个DNS服务器都必须定义自己的处理级别
    protected abstract boolean isLocal(Recorder r);

    protected abstract void sign(Recorder r);
}
