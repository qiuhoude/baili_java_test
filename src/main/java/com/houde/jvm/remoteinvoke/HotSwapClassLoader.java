package com.houde.jvm.remoteinvoke;

/**
 * @author qiukun
 * @create 2018-07-03 13:03
 **/
public class HotSwapClassLoader extends ClassLoader {
    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
