package com.houde.command_chain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I on 2018/3/26.
 */
public enum CommandEnum {
    ls("com.houde.command_chain.ls.LSCommand");
    private String value = "";

    // 定义构造函数，目的是Data(value)类型的相匹配
    private CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    // 返回所有的enum对象
    public static List<String> getNames() {
        CommandEnum[] commandEnum = CommandEnum.values();
        List<String> names = new ArrayList<String>();
        for (CommandEnum c : commandEnum) {
            names.add(c.name());
        }
        return names;
    }
}
