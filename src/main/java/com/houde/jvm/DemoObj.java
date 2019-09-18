package com.houde.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiukun
 * @create 2018-06-02 17:07
 **/
public class DemoObj {
    private String name;

    @Override
    public String toString() {
        return "I am DemoObj";
    }

    static List<DemoObj> objs = new ArrayList<>();



    public static void main(String[] args) {
//        Runnable r = () -> {
//            System.out.println("run");
//        };
//        new Thread(r).start();


        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
//        Integer d = 3;
//        Integer e = 128;
//        Integer f = 128;
        Long g = 3L;
//        System.out.println(c == d);
//        System.out.println(e == f);
//        System.out.println(c == (a + b));
//        System.out.println(c.equals(a + b));
//        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
    }




}
