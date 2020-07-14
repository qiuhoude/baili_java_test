package com.houde.reflects.util;

/**
 * @author qiukun
 * @create 2020-07-14 10:41
 */
public class ReflectException extends RuntimeException {

    public ReflectException(String message) {
        super(message);
    }

    public ReflectException(Throwable t) {
        super(t);
    }

    public ReflectException(String message, Throwable cause) {
        super(message, cause);
    }
}
