package com.houde.rpc.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

/**
 * @author qiukun
 * @create 2019-03-14 16:33
 */
public class JavassistProxy {
    private static final String methodStr = "public void sell() { System.out.print(\"JavassistProxy bookapi\"); }";

    public static BookApi createJavassistBytecodeDynamicProxy() throws Exception {
        ClassPool mPool = new ClassPool(true);
        //创建class
        CtClass ctc = mPool.makeClass(BookApi.class.getName() + "JavassistProxy");
        // 给class 添加接口
        ctc.addInterface(mPool.get(BookApi.class.getName()));
        // 添加构造器
        ctc.addConstructor(CtNewConstructor.defaultConstructor(ctc));
        // 添加方法
        ctc.addMethod(CtNewMethod.make(methodStr, ctc));
        ctc.defrost(); //解冻
        // 转成class
        Class<?> cc = ctc.toClass();
        BookApi bytecodeProxy = (BookApi) cc.newInstance();
        return bytecodeProxy;
    }
}