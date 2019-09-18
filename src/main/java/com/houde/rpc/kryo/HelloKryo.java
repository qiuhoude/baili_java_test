package com.houde.rpc.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.*;

/**
 * @author qiukun
 * @create 2019-03-13 18:51
 */
public class HelloKryo {

    static class Hi {
        String hi;
    }

    public static void main(String[] args) throws Exception {
//        testObjectStream();
//        testKryo();
        Hi hi = new Hi();
        hi.hi = "hello";
        final byte[] hiByte = serialize(hi);
        Hi hi2 = deserialize(hiByte);
        System.out.println(hi2.hi);
    }

    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(false);//关闭注册行为
            kryo.setReferences(true);//支持循环引用
            return kryo;
        }
    };

    public static byte[] serialize(Object obj) {
        Kryo kryo = kryoLocal.get();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (Output output = new Output(byteArrayOutputStream)) {//<1>
            kryo.writeClassAndObject(output, obj);//<2>
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }


    public static <T> T deserialize(byte[] bytes) {
        Kryo kryo = kryoLocal.get();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try (Input input = new Input(byteArrayInputStream)) {// <1>
            return (T) kryo.readClassAndObject(input);//<2>
        }
    }

    private static void testKryo() throws FileNotFoundException {
        Kryo kryo = kryoLocal.get();
        Output output = new Output(new FileOutputStream("student.db"));
        Student kirito = new Student("kirito");
        kryo.writeObject(output, kirito);
        output.close();
        Input input = new Input(new FileInputStream("student.db"));
        Student kiritoBak = kryo.readObject(input, Student.class);
        input.close();
        assert "kirito".equals(kiritoBak.getName());
        System.out.println(kiritoBak.getName());
    }

    private static void testObjectStream() throws IOException, ClassNotFoundException {
        // create a Student
        Student st = new Student("kirito");
        // serialize the st to student.db file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student.db"));
        oos.writeObject(st);
        oos.close();
        // deserialize the object from student.db
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student.db"));
        Student kirito = (Student) ois.readObject();
        ois.close();

        // assert
        assert "kirito1".equals(kirito.getName());
    }

}
