package com.houde.math;

import java.util.ArrayList;

/**
 * @author qiukun
 * @create 2019-01-25 17:19
 **/
public class Lesson5_test {

    //因数分解
    private static void factorization(int total, ArrayList<Integer> result) {
        if (total == 1) {
            if (!result.contains(1)) result.add(1);
            System.out.println(result);
            return;
        } else {
            for (int i = 1; i <= total; i++) {
                if (i == 1 && result.contains(1)) continue;
                ArrayList<Integer> newresult = (ArrayList<Integer>) result.clone();
                newresult.add(i);
                if (total % i != 0) {
                    continue;
                } else {
                    factorization(total / i, newresult);
                }
            }
        }

    }

    public static void main(String[] args) {
        factorization(7, new ArrayList<>());
    }

}
