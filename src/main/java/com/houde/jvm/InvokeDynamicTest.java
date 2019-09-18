package com.houde.jvm;

import java.lang.invoke.*;

/**
 * @author qiukun
 * @create 2018-06-29 18:22
 **/
public class InvokeDynamicTest {


    public static void main(String[] args) throws Throwable {
        INDY_BootstrapMethod().invokeExact("哈哈哈");
    }

    public static void testMethod(String s) {
        System.out.println("hello String：" + s);
    }

    public static CallSite BootstrapMethod(
            MethodHandles.Lookup lookup, String
            name,
            MethodType mt) throws Throwable {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
    }

    private static MethodType MT_BootstrapMethod() {
        return MethodType
                .fromMethodDescriptorString(
                        "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",
                        null);
    }

    private static MethodHandle MH_BootstrapMethod() throws Throwable {
        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootstrapMethod());
    }

    private static MethodHandle INDY_BootstrapMethod() throws Throwable {
        CallSite cs = (CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod",
                MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null));
        return cs.dynamicInvoker();
    }
}
