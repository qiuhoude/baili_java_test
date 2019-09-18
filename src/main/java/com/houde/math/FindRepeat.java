package com.houde.math;

/**
 * @author qiukun
 * @create 2019-01-26 18:35
 **/
public class FindRepeat {

    public static int getSpecialNum(int[] oArr, int n){
        int result = 0;
        for(int i = 0; i < oArr.length; i++){
            result = (result^oArr[i]);
        }
        for(int j = 1; j <= n; j++){
            result = result^j;
        }
        return result;
    }



    public static void main(String[] args) {
        int[] a = {1,5,6,8,48,6,5,5,56,5};
        System.out.println(getSpecialNum(a,4));

    }

}
