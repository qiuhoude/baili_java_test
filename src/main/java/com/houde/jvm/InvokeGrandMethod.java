package com.houde.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 * @author qiukun
 * @create 2018-06-29 19:23
 **/
public class InvokeGrandMethod {

    abstract class GrandFather {
        protected void say() {
            System.out.println("GrandFather say...");
        }
    }

    class Father extends GrandFather {
        @Override
        protected void say() {
            System.out.println("Father say");
        }
    }

    class Son extends Father {
        @Override
        protected void say() {
            //此处需要调用GrandFather的say方法
            System.out.println("Son say");
            MethodType mt = MethodType.methodType(void.class);
            try {
                // 1.7
//                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "say", mt, Son.class);
//                //调用祖父类里被父类覆写的方法
//                mh.invoke(this);

                // 1.8写法
                Field IMPL_LOOKUP = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                IMPL_LOOKUP.setAccessible(true);
                MethodHandles.Lookup lkp = (MethodHandles.Lookup)IMPL_LOOKUP.get(null);
                MethodHandle h1 = lkp.findSpecial(GrandFather.class, "say", mt, GrandFather.class);
                h1.invoke(this);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new InvokeGrandMethod().new Son()).say();
    }

}
