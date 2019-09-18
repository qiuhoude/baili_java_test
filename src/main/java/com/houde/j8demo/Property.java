package com.houde.j8demo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I on 2017/7/28.
 */
public class Property {
    String name;
    // 距离，单位:米
    Integer distance;
    // 销量，月售
    Integer sales;
    // 价格，这里简单起见就写一个级别代表价格段
    Integer priceLevel;

    public Property(String name, int distance, int sales, int priceLevel) {
        this.name = name;
        this.distance = distance;
        this.sales = sales;
        this.priceLevel = priceLevel;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", sales=" + sales +
                ", priceLevel=" + priceLevel +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(Integer priceLevel) {
        this.priceLevel = priceLevel;
    }

    public static void main(String[] args) {
        Property p1 = new Property("叫了个鸡", 1000, 500, 2);
        Property p2 = new Property("张三丰饺子馆", 2300, 1500, 3);
        Property p3 = new Property("永和大王", 580, 3000, 1);
        Property p4 = new Property("肯德基", 6000, 200, 4);
        List<Property> properties = Arrays.asList(p1, p2, p3, p4);
        properties.sort((x, y) -> x.distance.compareTo(y.distance));
        String name = properties.get(0).name;
        System.out.println("距离我最近的店铺是:" + name);

        String name2 = properties.stream().sorted((x, y) -> x.distance.compareTo(y.distance)).findFirst().get().name;
        System.out.println("距离我最近的店铺2是: " + name2);

        long count = properties.stream().filter(p -> p.sales > 1000).count();
        System.out.println("月销量大于1000的店铺个数: " + count);


//        try {
//            String c = Files.readAllLines(Paths.get("E:\\work\\develop\\protoc-2.5.0-win32_modernwar\\pbcreate.bat"))
//                    .stream().collect(Collectors.joining("\n"));
//            System.out.println(c);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        System.out.println(properties.size());
        List<Property> collect = properties.stream()
                .filter(p -> p.name.length() > 5).collect(Collectors.toList());
        System.out.println(collect.size());

        List<String> namesList = properties.stream()
                .map(p -> p.name).collect(Collectors.toList());
        System.out.println(namesList);


        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("apple", "click"));
        lists.add(Arrays.asList("boss", "dig", "qq", "vivo"));
        lists.add(Arrays.asList("c#", "biezhi"));

        long llCount = lists.stream()
                .flatMap(Collection::stream)
                .filter(str -> str.length() > 2).count();
        System.out.println(llCount);

        Property property = properties.stream()
                .max(Comparator.comparingInt(p -> p.priceLevel))
                .get();
        System.out.println("max: " + property);

        System.out.println("findAny: " + properties.stream().findAny().get());

        System.out.println("=============limit ===============");

        List<Property> limit = properties.stream()
                .sorted((x, y) -> x.distance.compareTo(y.distance))
                .filter(p -> p.distance > 600)
                .limit(20)
                .collect(Collectors.toList());
        limit.forEach(p -> System.out.println(p.distance));
//        System.out.println(limit.size() );
        Property property1 = limit.stream().min((x, y) -> x.distance - y.distance).orElse(null);
        System.out.println("min : " + property1.distance);
        System.out.println("=============limit ===============");

        Map<String, Integer> map = properties.stream()
                .collect(Collectors.toMap(Property::getName, x -> x.distance));
        System.out.println(map);
        map.forEach((k, v) -> System.out.println(v));

        List<Integer> inList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            inList.add(i);
        }
        System.out.println("=============end ===============");
        Integer n = inList.stream().filter(p -> p > 1000).findFirst().orElse(null);
        System.out.println("n: " + n);

    }

    static Random r = new Random();

    static public boolean randomBool() {
        return r.nextInt(100) % 2 == 0;
    }

    public static void print(Property property) {
        System.out.println(property.name + " " + property.distance);
    }
}
