package com.houde.guava.collection;

import com.google.common.collect.*;

/**
 * @author qiukun
 * @create 2019-08-05 14:54
 */
public class CollectionTest {

    static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void testMultiset() {
        Multiset<Person> counts = HashMultiset.create();
        Person haha = new Person("haha", 5);
        Person xixi = new Person("xixi", 1);
        Person penpen = new Person("penpen", 0);

        counts.add(haha);
        counts.add(haha);
        counts.add(haha);
        counts.add(haha);
        counts.add(xixi);

        System.out.println("haha 出现的次数 : " + counts.count(haha));
        counts.add(haha, 3);
        System.out.println("haha 出现的次数 : " + counts.count(haha));
        System.out.println("xixi 出现的次数 : " + counts.count(xixi));
        System.out.println("penpen 出现的次数 : " + counts.count(penpen));
        System.out.println("size : " + counts.size());
        counts.forEach(e -> System.out.print(e.name + ", "));
    }


    public static void testMultimap() {
        ListMultimap<String, Person> treeListMultimap =
                MultimapBuilder.treeKeys().arrayListValues().build();
        Person haha = new Person("haha", 5);
        Person xixi = new Person("xixi", 1);
        treeListMultimap.put(haha.name, haha);
        treeListMultimap.put(haha.name, haha);
        treeListMultimap.put(haha.name, haha);
        treeListMultimap.put(xixi.name, xixi);

//        treeListMultimap.remove(haha.name,haha);
        treeListMultimap.get(haha.name).add(haha);
        treeListMultimap.get(haha.name).clear();

        System.out.println("size : " + treeListMultimap.size());
        System.out.println("get : " + treeListMultimap.get(haha.name).size());


    }

    public static void testMultimap2() {
        SetMultimap<Integer, Integer> suitMap = MultimapBuilder.hashKeys().hashSetValues().build();
        suitMap.put(10, 101);
        suitMap.put(10, 101);
        suitMap.put(10, 102);
        suitMap.put(11, 110);
        Multiset<Integer> keys = suitMap.keys();
        for (Integer k : keys.elementSet()) {
            System.out.println(k + " count:" + keys.count(k));
        }


//        Set<Map.Entry<Integer, Integer>> entries = suitMap.entries();
//        for(Map.Entry<Integer, Integer> entry :entries){
//            System.out.println(entry.getKey()+" "+entry.getValue());
//        }

    }


    public static void testBiMap() {
        BiMap<String,Integer> nameToId = HashBiMap.create();
        nameToId.put("qiu",28);
        nameToId.put("houde",29);
        System.out.println(nameToId.inverse().get(28));
    }

    public static void main(String[] args) {
//        testMultiset();
//        testMultimap2();
        testBiMap();
    }
}
