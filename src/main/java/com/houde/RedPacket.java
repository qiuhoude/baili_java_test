package com.houde;

import java.util.Random;

/**
 * Created by I on 2018/6/6.
 */
public class RedPacket {
    private int money;
    private int size;
    private int remainSize;//剩余次数
    private int remainMoney;//剩余金额

    public static void main(String[] args) {
        RedPacket rp = new RedPacket(30, 10);
        int sum = 0;
        for (int i = 1; i <= rp.size; i++) {
            int curMoney = rp.receive();
            System.out.println(i + ", 本次领取: " + curMoney + "");
            sum += curMoney;
        }
        System.out.println("总领取数: " + sum);
    }

    public RedPacket(int money, int size) {
        this.money = money;
        this.size = size;
        this.remainSize = size;
        this.remainMoney = money;
    }

    public int receive2() {
        if (remainSize > 0 && remainMoney > 0) {
            if (remainSize == 1) {
                remainSize--;
                int m = remainMoney;
                remainMoney = 0;
                return m;
            }

            Random r = new Random();
            int curRemainMoney = remainMoney - 1 - remainSize;
            int min = 1; //
            int max = curRemainMoney / remainSize * 2;
            if (max < 1) {
                max = 1;
            }
            int money = r.nextInt(max);
            money = money <= min ? min : money;
            remainSize--;
            remainMoney -= money;
            return money;
        }
        return 0;
    }

    public int receive() {
        if (remainSize > 0 && remainMoney > 0) {
            remainSize--;
            int max = remainMoney - remainSize;
            Random random = new Random();
            int i = random.nextInt(max);
            int m = 1 + i;
            remainMoney -= m;
            return m;
        }
        return 0;
    }


    @Override
    public String toString() {
        return "RedPacket{" +
                "money=" + money +
                ", size=" + size +
                ", remainSize=" + remainSize +
                ", remainMoney=" + remainMoney +
                '}';
    }
}
