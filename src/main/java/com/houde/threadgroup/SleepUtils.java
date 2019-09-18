package com.houde.threadgroup;

import java.util.concurrent.TimeUnit;

/**
 * Created by I on 2018/1/3.
 */
public class SleepUtils {
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}
