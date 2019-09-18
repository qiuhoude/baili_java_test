package com.houde.test;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author qiukun
 * @create 2019-03-21 16:03
 */
public class PriorityQueueTest {
    public static void main(String args[]){
        PriorityQueue<People> queue = new PriorityQueue<People>(11,
                 Comparator.comparing(People::getAge));

        for (int i = 1; i <= 10; i++) {
            queue.add(new People("张"+ i, (new Random().nextInt(100))));
        }
        while (!queue.isEmpty()) {
            System.out.println(queue.poll().toString());
        }
    }
   static class People {
        String name;
        int age;
        public People(String name, int age){
            this.name = name;
            this.age = age;
        }

       public int getAge() {
           return age;
       }

       public void setAge(int age) {
           this.age = age;
       }

       public String toString() {
            return "姓名："+name + " 年龄：" + age;
        }
    }

}
