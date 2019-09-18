package com.houde.vm;

/**
 * Created by I on 2018/4/14.
 */
public class Case {
    public volatile int n;
    String a = "bbb";

    public synchronized void add() {
        n++;
    }

    public static void main(String[] args) {
        String a = "hello ";
        String b = "world";
        String c = a + b + "!";
        // String d = "hello world";
        String f = new StringBuilder().append(a).append(b).append("!").toString();
    }
}
