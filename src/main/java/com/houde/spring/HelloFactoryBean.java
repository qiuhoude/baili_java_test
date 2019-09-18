package com.houde.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author qiukun
 * @create 2018-12-08 17:34
 **/
public class HelloFactoryBean implements FactoryBean<Player> {

    @Override
    public Player getObject() throws Exception {
        Player player = new Player();
        player.setName("hello");
        player.setId(11);
        return player;
    }

    @Override
    public Class<?> getObjectType() {
        return Player.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
