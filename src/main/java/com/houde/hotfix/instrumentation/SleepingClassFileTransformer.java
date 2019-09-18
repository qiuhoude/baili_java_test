package com.houde.hotfix.instrumentation;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by I on 2017/12/27.
 */
public class SleepingClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {


            byte[] byteCode = classfileBuffer;

            if (className.equals("com/houde/hotfix/instrumentation/Sleeping")) {

                try {
                    ClassPool cp = ClassPool.getDefault();
                    CtClass cc = cp.get("com.houde.hotfix.instrumentation.Sleeping");
                    CtMethod m = cc.getDeclaredMethod("randomSleep");
                    m.addLocalVariable("elapsedTime", CtClass.longType);
                    m.insertBefore("elapsedTime = System.currentTimeMillis();");
                    m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                            + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");
                    byteCode = cc.toBytecode();
                    cc.detach();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return byteCode;
    }
}
