package com.houde.j8demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Created by I on 2017/7/28.
 */
public class Java8Time {

    public static void testLoacalDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println("localDate: " + localDate);

        LocalDate.of(2017, 07, 20);
        LocalDate.parse("2017-07-20");

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        System.out.println("tomorrow: " + tomorrow);

        LocalDate prevMonth = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        System.out.println(prevMonth);

        DayOfWeek thursday = LocalDate.parse("2017-07-20").getDayOfWeek();

        System.out.println("周四: " + thursday);
        int twenty = LocalDate.parse("2017-07-20").getDayOfMonth();
        System.out.println("twenty: " + twenty);

        boolean leapYear = LocalDate.now().isLeapYear();
        System.out.println("是否闰年: " + leapYear);

        LocalDate firstDayOfMonth = LocalDate.now()
                .with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("这个月的第一天: " + firstDayOfMonth);

        firstDayOfMonth = firstDayOfMonth.withDayOfMonth(1);
        System.out.println("这个月的第一天: " + firstDayOfMonth);

        LocalDate birthday = LocalDate.of(2009, 07, 28);
        MonthDay birthdayMd = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
        MonthDay today = MonthDay.from(LocalDate.now());
        System.out.println("今天是否是我的生日: " + today.equals(birthdayMd));
    }

    public static void testLocalTime() {
        LocalTime now = LocalTime.now();
        System.out.println("现在的时间: " + now);

        LocalTime nowTime = LocalTime.parse("15:02");
        System.out.println("时间是: " + nowTime);

        LocalTime nowTime1 = LocalTime.of(15, 02);
        System.out.println("时间是: " + nowTime1);

        LocalTime nextHour = LocalTime.parse("15:02").plus(1, ChronoUnit.HOURS);
        System.out.println("下一个小时: " + nextHour);

        int hour = LocalTime.parse("15:02").getHour();
        System.out.println("小时: " + hour);
        int minute = LocalTime.parse("15:02").getMinute();
        System.out.println("分钟: " + minute);

        boolean isBefore = LocalTime.parse("15:02").isBefore(LocalTime.parse("16:02"));
        boolean isAfter = LocalTime.parse("15:02").isAfter(LocalTime.parse("16:02"));
        System.out.println("isBefore: " + isBefore);
        System.out.println("isAfter: " + isAfter);
    }

    public static void testLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("现在: " + now);

        LocalDateTime tomorrow = now.plusDays(1);
        System.out.println("明天的这个时间: " + tomorrow);
        LocalDateTime minusTowHour = now.minusHours(2);
        System.out.println("两小时前: " + minusTowHour);

        //格式化
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("默认格式化: " + now);
        System.out.println("自定义格式化: " + now.format(dateTimeFormatter));
        LocalDateTime localDateTime = LocalDateTime.parse("2017-07-20 15:27:44", dateTimeFormatter);
        System.out.println("字符串转LocalDateTime: " + localDateTime);

        String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        System.out.println("日期转字符串: " + dateString);


    }

    public static void changeDate() {

        LocalDate initialDate = LocalDate.parse("2017-07-20");
        LocalDate finalDate = initialDate.plus(Period.ofDays(11));
        System.out.println("初始化日期: " + initialDate);
        System.out.println("加日期之后: " + finalDate);
        long between = ChronoUnit.WEEKS.between(initialDate, finalDate);
        System.out.println("差距天数: " + between);

        Date date = Date.from(Instant.now());
        Instant instant = date.toInstant();

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        Date dd =  Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(dd);
    }

    /*
    ZoneId: 时区ID，用来确定Instant和LocalDateTime互相转换的规则
    Instant: 用来表示时间线上的一个点
    LocalDate: 表示没有时区的日期, LocalDate是不可变并且线程安全的
    LocalTime: 表示没有时区的时间, LocalTime是不可变并且线程安全的
    LocalDateTime: 表示没有时区的日期时间, LocalDateTime是不可变并且线程安全的
    Clock: 用于访问当前时刻、日期、时间，用到时区
    Duration: 用秒和纳秒表示时间的数量
     */
    public static void main(String[] args) {
//        testLocalTime();
//        testLocalDateTime();
        changeDate();
    }
}
