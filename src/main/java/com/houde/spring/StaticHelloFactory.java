package com.houde.spring;

/**
 * @author qiukun
 * @create 2018-12-08 17:39
 **/
public class StaticHelloFactory {
    public static Player getHello() {
        Player player = new Player();
        player.setName("created by StaticHelloFactory");
        return player;
    }

    public Player getPlayer() {
        Player player = new Player();
        player.setName("created by StaticHelloFactory111");
        return player;
    }
}
