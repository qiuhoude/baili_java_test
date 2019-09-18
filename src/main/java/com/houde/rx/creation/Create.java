package com.houde.rx.creation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by I on 2017/12/27.
 */
public class Create {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                map.put(i,i+":");
            }
        }).start();

        new Thread(() -> {
            for (int i = 100000; i < 900000; i++) {
                map.put(i,i+":");
            }
        }).start();

        new Thread(() -> {
            for (int i = 900000; i < 10000000; i++) {
                map.put(i,i+":");
            }
        }).start();
        new Thread(() -> {
            for (int i = 10000000; i < 100000001; i++) {
                map.put(i,i+":");
            }
        }).start();

    }
}
