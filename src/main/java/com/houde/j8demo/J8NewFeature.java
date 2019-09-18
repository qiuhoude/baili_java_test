package com.houde.j8demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by I on 2017/7/28.
 */
public class J8NewFeature {
    List<Person> persons = Collections.<Person>emptyList();

    @Repeatable(Basics.class)
    @interface Basic {
        String name();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Basics {
        Basic[] value();
    }

    @Basics({@Basic(name = "fix"), @Basic(name = "todo")})
    static class Person {
        String name;

        public String getName() {
            return name;
        }
    }


    public static void main(String[] args) throws IOException {
//        try {
//            Thread.sleep(2000);
//            FileInputStream fis = new FileInputStream("/a/b.txt");
//            System.out.println("结束");
//        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("结束程序结束");


//        Path path = FileSystems.getDefault().getPath("/opt/www/", "aaa.txt");
//        byte[] data   = Files.readAllBytes(Paths.get("/home/biezhi/a.txt"));
//        String content = new String(data, StandardCharsets.UTF_8);


//        String str = String.join(",", "a", "b", "c");
//        System.out.println(str);
//
//        Basic[] basics = Person.class.getAnnotationsByType(Basic.class);
//        Arrays.asList(basics).forEach(a -> {
//            System.out.println(a.name());
//        });
//
//        //过滤掉空
//        Stream.of("a", "c", null, "d")
//                .filter(Objects::nonNull)
//                .forEach(System.out::println);
        Person u = new Person();
        u.name = "aa";
        String aa = getPersonNameByOptional(null);
        System.out.println(aa);
    }

    public static String getPersonNameByOptional(Person user) {
        Optional<String> userName = Optional.ofNullable(user).map(Person::getName);
//        return userName.get();
        return userName.orElse("nn");
    }

    public static void tryWithResources() throws IOException {
        try (InputStream ins = new FileInputStream("/home/biezhi/a.txt")) {
            char charStr = (char) ins.read();
            System.out.print(charStr);
        }
    }
}
