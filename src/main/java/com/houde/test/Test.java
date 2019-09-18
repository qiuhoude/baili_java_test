package com.houde.test;

import com.houde.protobuf.PersonEntity;

import java.io.IOException;

/**
 * Created by I on 2017/6/20.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //模拟将对象转成byte[]，方便传输
        PersonEntity.Person.Builder builder = PersonEntity.Person.newBuilder();
        builder.setId(1);
        builder.setName("ant");
//        builder.setEmail("ghb@soecode.com");
//        builder.setLikes(0,"骑车");
//        builder.setLikes(1,"驾驶");
        builder.addLikes("足球");
        builder.addLikes("篮球");
        PersonEntity.Person person = builder.build();
        System.out.println("before :"+ person.toString());

        System.out.println("===========Person Byte==========");
        for(byte b : person.toByteArray()){
            System.out.print(b);
        }
        System.out.println();
        System.out.println(person.toByteString());
        System.out.println("================================");

        //模拟接收Byte[]，反序列化成Person类
        byte[] byteArray =person.toByteArray();
        PersonEntity.Person p2 = PersonEntity.Person.parseFrom(byteArray);
        System.out.println("after :" +p2.toString());
        System.out.println("email :" +p2.getEmail());
        System.out.println(p2.getLikesList());
    }
}
