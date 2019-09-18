package com.houde;

import java.util.*;

/**
 * Created by I on 2017/7/11.
 */
public class Other {
    public static void main(String[] args) {
        // for (int i = 1; i < 6; i++) {
        // int price = 200; // 原价
        // int discount = 100 - i * 10;// 折扣
        // int discountPrice = (int) ((discount * 1.0f) / 100 * (price * 1.0f));
        // System.out.println(discountPrice);
        // }
        // addCabinetLeadExp();
        // int lead = 820;
        // int leadLine = 1;
        // lead *= (1 + leadLine*1.0f / 4);
        // System.out.println(lead );

        // Object[] arr1 = {null, null, null, null,null, null};
        // Object[] arr2 = Arrays.copyOf(arr1, 10);

        // for(int i = 0; i < arr2.length; i++)
        // System.out.print(arr2[i] + " ");
        // System.out.println();
        // Calendar c = Calendar.getInstance();
        // int day = c.get(Calendar.DAY_OF_MONTH) + 1;
        // c.set(Calendar.DAY_OF_MONTH, day);
        // c.set(Calendar.HOUR_OF_DAY, 12);
        // c.set(Calendar.MINUTE, 0);
        // c.set(Calendar.SECOND, 0);
        // c.set(Calendar.MILLISECOND, 0);
        //
        // Date date = c.getTime();
        // System.out.println(date);

        // List<Integer> integers = new ArrayList<>();
        // for (int i = 0; i < 241; i++) {
        // integers.add(i);
        // }
        // List<List<Integer>> group = new ArrayList<>();
        //
        // int count = 0;
        // for (Integer p : integers) {
        // if (count % 5 == 0) {
        // List<Integer> pList = new ArrayList<>();
        // group.add(pList);
        // }
        // group.get(count / 5).add(p);
        // count++;
        // }
        //
        // for (List<Integer> list : group) {
        // System.out.println(list.toString());
        // }
        // int v= integers.stream().min(Comparator.comparingInt(p -> p.intValue())).get();
        // System.out.println(v);
        //
        // int ceil = (int) Math.ceil(0.000000000000000000001*100);
        // System.out.println(ceil);

        // List<Integer> integers = new ArrayList<>();
        // integers.add(1);
        // integers.add(2);
        // integers.add(3);
        // integers.add(4);
        //
        // int swp = 5;
        // int elem = 6;
        // int index = integers.indexOf(swp);
        // if (index != -1) {
        // integers.add(index, elem);
        // integers.remove(index + 1);
        // }

        // long[] camps = { 0, 200, 23, 32 };
        // int campIndex = 1;
        // for (int i = 1; i <= 3; i++) {
        // if (camps[campIndex] <= camps[i]) {
        // campIndex = i;
        // }
        // }
        // Map<Integer, Integer> vipMap = new HashMap<>();
        // vipMap.put(1, 32);
        // vipMap.put(2, 0);
        // vipMap.put(3, 0);
        // vipMap.put(4, 5);
        // vipMap.put(5, 32);
        // vipMap.put(6, 212);
        //
        // for (int i = 1; i <= 6; i++) {
        // System.out.println("vip:" + i + ", 人数:" + getTotalVIPCnt(i, vipMap));
        // }
        // shuffle();

//         CRC32 crc32 = new CRC32();
//         crc32.update("abcdfg1".getBytes());
//         System.out.println(crc32.getValue());

//        DecimalFormat df1 = new DecimalFormat("00");
//        System.out.println(df1.format(100));
        Map<String, String> params = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        params.put("appid", "aaa");
        params.put("userid", "bbb");
        params.put("times", "cccc");
        params.put("token", "ddd");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> kv : params.entrySet()) {
            sb.append(kv.getKey() + "=" + kv.getValue() + "&");
        }

//        System.out.println(sb);


//        Object[] objArr = {"xxx", 1, true, false, 1.0f, 10.33};
//        String s = Arrays.toString(objArr);
//        System.out.println(s);

        testArrPara(1);
    }


    private static void testArrPara(int a, Object... param) {
        if(param!=null && param.length>0) {
            String s = Arrays.toString(param);
            System.out.println(s);
        }else {
            System.out.println(0);
        }
    }


    public static void testShuffle() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
        int nTemp;
        for (int i = 0; i < 8; i++) {
            int nPos = (int) (Math.random() * 10 % 8);
            nTemp = a[i];
            a[i] = a[nPos];
            a[nPos] = nTemp;
        }
        for (int i : a) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }

    public static void shuffle() {
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List list = new ArrayList();
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + ", ");
            list.add(x[i]);
        }
        System.out.println();

        Collections.shuffle(list);

        Iterator ite = list.iterator();
        while (ite.hasNext()) {
            System.out.print(ite.next().toString() + ", ");
        }
    }

    public static int getTotalVIPCnt(int vipLv, Map<Integer, Integer> vipMap) {
        final int maxVipLV = 6;
        int count = 0;
        for (int i = vipLv; i <= maxVipLV; i++) {
            Integer cnt = vipMap.get(i);
            cnt = cnt != null ? cnt : 0;
            count += cnt;
        }
        return count;
    }

    public static void addCabinetLeadExp() {
        // 初始化等级
        Map<Integer, Integer> needMap = new HashMap<>();
        int maxLv = 42;

        for (int i = 1; i <= maxLv; i++) {
            needMap.put(i, 100);
        }
        int curLv = 4;
        int curExp = 50;
        int addExp = 55;

        // 满级之不累计经验
        // if (curLv >= maxLv) {
        // curExp = 0;
        // }
        while (curLv < maxLv) {
            long need = needMap.get(curLv);
            if (curExp + addExp >= need) {
                addExp -= (need - curExp);
                curExp = 0;
                curLv += 1;// 升一级
            } else {
                curExp += addExp;
                addExp = 0;
                break;
            }
        }

        // do {
        // long need = needMap.get(curLv);
        // if (curExp + addExp >= need) {
        // addExp -= (need - curExp);
        // curLv += 1;// 升一级
        // curExp = 0;
        // } else {
        // curExp += addExp;
        // addExp = 0;
        // break;
        // }
        // } while (addExp > 0 && curLv < maxLv);

        System.out.println("curLv:" + curLv);
        System.out.println("curExp:" + curExp);
        System.out.println("addExp:" + addExp);
    }

    private static void arrRm0() {
        int[] heroIds = {0, 2, 1, 4, 3};
        printArr(heroIds);
        List<Integer> heroList = new ArrayList<>();
        for (int i = 1; i < heroIds.length; i++) {
            int hid = heroIds[i];
            if (hid != 0) {
                heroList.add(hid);
            }
        }
        for (int i = 0; i < heroIds.length - 1; i++) {
            int pos = i + 1;
            if (i < heroList.size()) {
                int hId = heroList.get(i);
                heroIds[pos] = hId;
            } else {
                // 清空
                heroIds[pos] = 0;
            }
        }

        printArr(heroIds);
    }

    private static void printArr(int[] arr) {
        String s = "";
        for (int a : arr) {
            s = s + "," + a;
        }
        System.out.println(s);
    }
}
