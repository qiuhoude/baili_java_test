package com.houde.apt;

/**
 * @author qiukun
 * @create 2018-05-04 11:01
 **/
@Immutable
public class MutableClass {
    private String name;

    public MutableClass(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
