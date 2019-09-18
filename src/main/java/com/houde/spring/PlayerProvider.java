package com.houde.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author qiukun
 * @create 2018-12-08 17:57
 **/
public class PlayerProvider  implements ApplicationContextAware {
    private Player player;
    private ApplicationContext applicationContext;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
