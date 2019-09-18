package com.houde.vm;

import java.io.File;

/**
 * Created by I on 2018/4/9.
 */
public class SizeOfObjectTest {

    /**
     *
     * -XX:-UseCompressedOops: mark/8 + metedata/8 + 8 + padding/0 = 24
     */
    static class A {
        int a;
        char c;
    }

    static class A2 {
        char b;
        int a;
    }

    static class A3 {
        char b;
        boolean c;
        double d;
        short e;
        boolean f;
    }

    static class A4 {
        char b;
        short e;
        boolean d;
        boolean f;
        double a;
    }

    static class A5 {
        int[] b = new int[10];
    }

    /**
     * -XX:+UseCompressedOops: mark/4 + metedata/8 + 4 + 4 + padding/4 = 24 -XX:-UseCompressedOops: mark/8 + metedata/8
     * + 4 + 4 = 24
     */
    static class B {
        int a;
        int b;
    }

    /**
     * -XX:+UseCompressedOops: mark/4 + metedata/8 + 4 + 4 + padding/4 = 24 -XX:-UseCompressedOops: mark/8 + metedata/8
     * + 8 + 4 + padding/4 = 32
     */
    static class B2 {
        int b2a;
        Integer b2b;
    }

    /**
     * 不考虑对象头： 4 + 4 + 4 * 3 + 3 * sizeOf(B)
     */
    static class C extends A {
        int ba;
        B[] as = new B[3];

        C() {
            for (int i = 0; i < as.length; i++) {
                as[i] = new B();
            }
        }
    }

    static class A1 {
        private double a;
        // char b;

        public void setA1() {
            a = 10;
        }

        public void showA1() {
            System.out.println("A1 a: " + a);
        }
    }

    static class C1 extends A1 {
        double ba;
        private double a;
        B[] as = new B[3];

        public void set_C1() {
            a = 100;
        }

        public void show_C1() {
            System.out.println("C1 a: " + a);
        }
    }

    static class D extends B {
        int da;
        Integer[] di = new Integer[3];
    }

    /**
     * 会算上A的实例字段
     */
    static class E extends A {
        int ea;
        int eb;
    }

    static class AA extends A {
        int a;
        char c;
    }

    static class AAA extends AA {
        int a;
        char c;
    }

    public static void main(String[] args) throws IllegalAccessException {
        C1 c1 = new C1();
        c1.setA1();
        c1.showA1();
        c1.set_C1();
        c1.showA1();
        c1.show_C1();
        System.out.println(new File("target/classes").getAbsolutePath());
        System.out.println("sizeOf(new Object())=" + SizeOfAgent.sizeOf(new Object()));
        System.out.println("sizeOf(new A())=" + SizeOfAgent.sizeOf(new A()));
        System.out.println("sizeOf(new A1())=" + SizeOfAgent.sizeOf(new A1()));
        System.out.println("sizeOf(new A3())=" + SizeOfAgent.sizeOf(new A3()));
        System.out.println("sizeOf(new A4())=" + SizeOfAgent.sizeOf(new A4()));
        System.out.println("sizeOf(new B())=" + SizeOfAgent.sizeOf(new B()));
        System.out.println("sizeOf(new B2())=" + SizeOfAgent.sizeOf(new B2()));
        System.out.println("sizeOf(new B[3])=" + SizeOfAgent.sizeOf(new B[3]));
        System.out.println("sizeOf(new C())=" + SizeOfAgent.sizeOf(new C()));
        System.out.println("sizeOf(new C1())=" + SizeOfAgent.sizeOf(new C1()));
        System.out.println("fullSizeOf(new C())=" + SizeOfAgent.fullSizeOf(new C()));
        System.out.println("sizeOf(new D())=" + SizeOfAgent.sizeOf(new D()));
        System.out.println("fullSizeOf(new D())=" + SizeOfAgent.fullSizeOf(new D()));
        System.out.println("sizeOf(new int[0])=" + SizeOfAgent.sizeOf(new int[0]));
        System.out.println("sizeOf(new int[3])=" + SizeOfAgent.sizeOf(new int[3]));
        System.out.println("sizeOf(new Integer(1))=" + SizeOfAgent.sizeOf(new Integer(1)));
        System.out.println("sizeOf(new Integer[0])=" + SizeOfAgent.sizeOf(new Integer[0]));
        System.out.println("sizeOf(new Integer[1])=" + SizeOfAgent.sizeOf(new Integer[1]));
        System.out.println("sizeOf(new Integer[2])=" + SizeOfAgent.sizeOf(new Integer[2]));
        System.out.println("sizeOf(new Integer[3])=" + SizeOfAgent.sizeOf(new Integer[3]));
        System.out.println("sizeOf(new Integer[4])=" + SizeOfAgent.sizeOf(new Integer[4]));
        System.out.println("sizeOf(new A[3])=" + SizeOfAgent.sizeOf(new A[3]));
        System.out.println("sizeOf(new E())=" + SizeOfAgent.sizeOf(new E()));
        System.out.println("sizeOf(new AA())=" + SizeOfAgent.sizeOf(new AA()));
        System.out.println("sizeOf(new AAA())=" + SizeOfAgent.sizeOf(new AAA()));
    }
}
