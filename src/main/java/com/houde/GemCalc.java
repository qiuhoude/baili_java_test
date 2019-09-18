package com.houde;

import java.util.Random;

/**
 * @author qiukun
 * @create 2018-05-05 18:08
 **/
public class GemCalc {


    public static long combineInt2Long(int low, int high) {
        return ((long) low & 0xFFFFFFFFl) | (((long) high << 32) & 0xFFFFFFFF00000000l);
    }

    public static int[] separateLong2int(Long val) {
        int[] ret = new int[2];
        ret[0] = (int) (0xFFFFFFFFl & val);
        ret[1] = (int) ((0xFFFFFFFF00000000l & val) >> 32);
        return ret;
    }

    public static void main(String[] args) {
//        long timeAndCnt = 0;
//        int time = 1;
//        int cnt = 0;
//        timeAndCnt = combineInt2Long(time, cnt);
//        System.out.println(timeAndCnt);
//        int[] ints = separateLong2int(timeAndCnt);
//        System.out.println(ints[0] + ":" + ints[1]);

        LeftMoneyPackage p = new LeftMoneyPackage();
        p.remainSize = 10;
        p.remainMoney = 50.00;
        for (int i = 1; i <= 10; i++) {
            double money = getRandomMoney(p);
            System.out.println(i+"---------"+money+ "----"+p  );
        }
    }

    static class LeftMoneyPackage {
        public int remainSize;  // 剩余的红包数量
        public double remainMoney; // 剩余的钱

        @Override
        public String toString() {
            return "LeftMoneyPackage{" +
                    "remainSize=" + remainSize +
                    ", remainMoney=" + remainMoney +
                    '}';
        }
    }

    public static double getRandomMoney(LeftMoneyPackage _leftMoneyPackage) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_leftMoneyPackage.remainSize == 1) {
            _leftMoneyPackage.remainSize--;
            return (double) Math.round(_leftMoneyPackage.remainMoney * 100) / 100;
        }
        Random r = new Random();
        double min = 0.01; //
        double max = _leftMoneyPackage.remainMoney / _leftMoneyPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 0.01 : money;
        money = Math.floor(money * 100) / 100;
        _leftMoneyPackage.remainSize--;
        _leftMoneyPackage.remainMoney -= money;
        return money;
    }

}
