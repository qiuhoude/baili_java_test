package com.houde.eventbus;

/**
 * Created by I on 2017/7/26.
 */
public class Msg {
    public String code;
    public Object object;

    public Msg(String code, Object object){
        this.code = code;
        this.object = object;
    }
}
