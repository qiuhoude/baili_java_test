package com.houde.jvm;

import java.io.IOException;
import java.io.InputStream;

/**
 * 两个不同的classload加载同一个class, instanceof会是false
 * @author qiukun
 * @create 2018-06-27 16:36
 **/
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")+1) + ".class";

                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return super.loadClass(name);
            }
        };
        try {
            Object obj = myLoader.loadClass("com.houde.jvm.ClassLoaderTest").newInstance();
            System.out.println(obj.getClass());
            System.out.println(obj instanceof com.houde.jvm.ClassLoaderTest);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
