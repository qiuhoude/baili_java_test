package com.houde.j8demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by I on 2017/7/26.
 */
public class FunctionInterfaceDemo {

    @FunctionalInterface
    interface Predicate<T> {
        boolean test(T t);
    }

    /**
     * 执行Predicate判断
     *
     * @param age 年龄
     * @param predicate Predicate函数式接口
     * @return 返回布尔类型结果
     */
    public static boolean doPredicate(int age, Predicate<Integer> predicate) {
        return predicate.test(age);
    }

    /**
     * 消费型接口示例
     * 
     * @param money
     * @param consumer
     */
    public static void donation(Integer money, Consumer<Integer> consumer) {
        consumer.accept(money);
    }

    /**
     * 供给型接口示例
     * 
     * @param num
     * @param supplier
     * @return
     */
    public static List<Integer> supply(Integer num, Supplier<Integer> supplier) {
        List<Integer> resultList = new ArrayList<Integer>();
        for (int x = 0; x < num; x++)
            resultList.add(supplier.get());
        return resultList;
    }

    /**
     * 函数型接口示例
     * 
     * @param str
     * @param function
     * @return
     */
    public static Integer convert(String str, Function<String, Integer> function) {
        return function.apply(str);
    }

    /**
     * 断言型
     * 
     * @param fruit
     * @param predicate
     * @return
     */
    public static List<String> filter(List<String> fruit, Predicate<String> predicate) {
        List<String> f = new ArrayList<>();
        for (String s : fruit) {
            if (predicate.test(s)) {
                f.add(s);
            }
        }
        return f;
    }

    public static void main(String[] args) {
        // boolean isAdult = doPredicate(20, x -> x >= 18);
        // System.out.println(isAdult);
        //
        // donation(1000, money -> System.out.println("好心的麦乐迪为Blade捐赠了" + money + "元"));

        // List<Integer> list = supply(10, () -> (int) (Math.random() * 100));
        // // list.forEach(System.out::println);
        // list.forEach(FunctionInterfaceDemo::add);

        // Integer value = convert("28", x -> Integer.parseInt(x));

//        List<String> fruit = Arrays.asList("香蕉", "哈密瓜", "榴莲", "火龙果", "水蜜桃");
//        List<String> newFruit = filter(fruit, (f) -> f.startsWith("火"));
//        System.out.println(newFruit);

        List<Integer> list = Arrays.asList(2, 7, 3, 1, 8, 6, 4);
        list.sort(Comparator.naturalOrder());
        System.out.println(list);

    }

    public static void add(Object a) {
        a = +1;
        System.out.println(a);
    }
}
