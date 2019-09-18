package com.houde;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: hodue
 * Date: 2018-05-06
 * Time: 20:54
 */
public class GemTest {

    static class Gem {
        public int lv;
        public int cnt;
    }

    static class GemConf {
        public int lv;
        public int upLvNeedCnt;
        public int upLVNeedExp;

        public GemConf(int lv, int upLvNeedCnt) {
            this.lv = lv;
            this.upLvNeedCnt = upLvNeedCnt;
        }

        @Override
        public String toString() {
            return "GemConf{" +
                    "lv=" + lv +
                    ", upLvNeedCnt=" + upLvNeedCnt +
                    ", upLVNeedExp=" + upLVNeedExp +
                    '}';
        }
    }

    static class GemConfManage {

        public Map<Integer, GemConf> confMap = new HashMap<>();

        public void init() {
            confMap.put(1, new GemConf(1, 3));
            confMap.put(2, new GemConf(2, 3));
            confMap.put(3, new GemConf(3, 3));
            confMap.put(4, new GemConf(4, 3));
            confMap.put(5, new GemConf(5, 3));
            confMap.put(6, new GemConf(6, 3));
            confMap.put(7, new GemConf(7, 3));
            confMap.put(8, new GemConf(8, 3));
            confMap.put(9, new GemConf(9, 3));
            confMap.put(10, new GemConf(10, 3));
            confMap.put(11, new GemConf(11, 3));
            confMap.put(12, new GemConf(12, 3));
            confMap.put(13, new GemConf(13, 3));

            for (int i = 1; i <= confMap.size(); i++) {
                calExp(i);
            }
//            confMap.values().forEach(System.out::println);
        }

        private void calExp(int lv) {
            GemConf curConf = confMap.get(lv);
            if (lv == 1) {
                curConf.upLVNeedExp = 1;
                return;
            }
            GemConf perConf = confMap.get(lv - 1);
            int exp = perConf.upLVNeedExp * curConf.upLvNeedCnt;
            curConf.upLVNeedExp = exp;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        GemConfManage confM = new GemConfManage();
        confM.init();
        Map<Integer, Integer> myBag = new HashMap<>();
        myBag.put(7, 8);
        myBag.put(8, 7);
        myBag.put(9, 1);
        Map<Integer, Integer> cost = neeCost(confM.confMap, myBag, 10);
        System.out.println(cost);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    public static Map<Integer, Integer> neeCost(Map<Integer, GemConf> conf, Map<Integer, Integer> myBag, int upLv) {
        Map<Integer, Integer> needCost = new HashMap<>();
        GemConf upGem = conf.get(upLv);
        int needSumExp = upGem.upLVNeedExp;
        int lv = upLv;
//        int needCnt = upGem.upLvNeedCnt;
        while (--lv >= 1 && needSumExp > 0) {
            Integer hasCnt = myBag.get(lv);
            hasCnt = hasCnt == null ? 0 : hasCnt;
            if (hasCnt == 0) continue;
            GemConf curConf = conf.get(lv);
            int hasExp = hasCnt * curConf.upLVNeedExp;
            if (hasExp >= needSumExp) {
                int needCnt = needSumExp / curConf.upLVNeedExp;
                needCost.put(lv, needCnt);
                return needCost;
            } else {
                needSumExp -= hasExp;
                needCost.put(lv, hasCnt);
                continue;
            }
        }
        return null;
    }
}
