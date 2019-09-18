package com.houde;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by I on 2017/6/28.
 */
public class SortTest {
    static class P {

        public P(int state, int time, String name) {
            this.state = state;
            this.time = time;
            this.name = name;
        }

        public int state;
        public int time;
        public String name;

        @Override
        public String toString() {
            return "P{" + "state=" + state + ", time=" + time + ", name='" + name + '\'' + '}';
        }
    }

    public static void main(String[] args) throws ParseException {
        // Map<Integer, P> map = new HashMap<Integer, P>();
        // map.put(5, new P(0, 106, "haha"));
        // map.put(4, new P(1, 103, "xixi"));
        // map.put(8, new P(0, 108, "mimi"));
        // map.put(1, new P(1, 100, "qiu"));
        // map.put(2, new P(2, 101, "kun"));
        // map.put(6, new P(0, 55, "bibi"));
        // map.put(3, new P(2, 102, "houde"));
        // map.put(7, new P(1, 109, "lili"));
        // System.out.println("map: " + map);
        // System.out.println("==============================");
        // // Collection<P> values = map.values();
        // // P rp = null;
        // // for (P p : values) {
        // // if (p.name.equals("kun")) rp = p;
        // // p.name = p.name + "_end";
        // // }
        // // values.remove(rp);
        // ArrayList<P> list = new ArrayList<P>();
        // list.addAll(map.values());
        // System.out.println(list);
        // System.out.println("================yuanshi=============");
        // Collections.sort(list, new Comparator<P>() {
        // public int compare(P o1, P o2) {
        // // o1 > o2 1 升序 ; -1 降序
        // if (o1.state == o2.state) {
        // if (o1.time > o2.time) {
        // return 1;
        // } else if (o1.time < o2.time) {
        // return -1;
        // } else {
        // return 0;
        // }
        //
        // } else if (o2.state == 1) {
        // return 1;
        // } else if (o1.state == 1) {
        // return -1;
        // } else if (o1.state == 2) {
        // return -1;
        // } else if (o2.state == 2) {
        // return 1;
        // }
        // return 0;
        // }
        // });
        // System.out.println("================state=============");
        // System.out.println(list);
        //
        // System.out.println("map: " + map);

        // rank();

        // List<Integer> list = new ArrayList<>();
        // for (int i = 0; i < 52; i++) {
        // list.add(i);
        // }
        //
        // int page = 6;
        // final int pageCount = 10;// 每页显示多少个
        // int begin = (page - 1) * pageCount;
        // int end = begin + pageCount;
        // int index = 0;
        // Iterator<Integer> it = list.iterator();
        // while (it.hasNext()) {
        // if (index >= end) {
        // break;
        // }
        // int i = it.next();
        // if (index >= begin) {
        // System.out.println(i);
        // }
        // ++index;
        // }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date start = sdf.parse("2017-11-25");
        Date now = new Date();
        int dayiy = dayiy(start, now);
        System.out.println();
        int startid = 4;
        int maxId = 14;
        int sum = startid + dayiy;
        int curId = (sum % maxId);
        curId = curId == 0 ? maxId : curId;
        // System.out.println(curId);

        Map<String, Integer> cm = new ConcurrentHashMap<>();
        for (int i = 0; i < 50; i++) {
            cm.put(String.valueOf(i), i);
        }
        System.out.println(cm);
        Iterator<Map.Entry<String, Integer>> it = cm.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, Integer> next = it.next();
            Integer value = next.getValue();
            if(value%2==0){
                it.remove();
            }
        }
        System.out.println(cm);

        int cnt = 1;
        boolean b =  cnt--==0;
        System.out.println("b:"+b+", cnt:"+cnt);
    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n;
    }

    static public int dayiy(Date origin, Date now) {
        Calendar orignC = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        orignC.setTime(origin);
        orignC.set(Calendar.HOUR_OF_DAY, 0);
        orignC.set(Calendar.MINUTE, 0);
        orignC.set(Calendar.SECOND, 0);
        orignC.set(Calendar.MILLISECOND, 0);

        calendar.setTime(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return (int) ((calendar.getTimeInMillis() - orignC.getTimeInMillis()) / (24 * 3600 * 1000)) + 1;
    }

    private static void rank() {
        List<Lord> list = new LinkedList<>();
        list.add(new Lord("hehe", 1, 30, 9000));
        list.add(new Lord("xixi", 2, 5, 9000));
        list.add(new Lord("qiu", 5, 60, 9000));
        list.add(new Lord("houde", 3, 55, 9001));
        list.add(new Lord("mimi", 3, 70, 9000));
        list.add(new Lord("lala", 5, 56, 10000));

        System.out.println("====================================");
        Collections.sort(list, new Comparator<Lord>() {
            @Override
            public int compare(Lord o1, Lord o2) {
                // (军阶>战力>等级)
                long r1 = o1.getRanks();
                long r2 = o2.getRanks();

                long f1 = o1.getFight();
                long f2 = o2.getFight();

                long lv1 = o1.getLevel();
                long lv2 = o2.getLevel();

                if (r1 == r2) {
                    if (f1 == f2) {
                        if (lv1 == lv2) {
                            return 0;
                        } else if (lv1 > lv2) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else if (f1 > f2) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else if (r1 > r2) {
                    return -1;
                } else if (r1 < r2) {
                    return 1;
                }
                return 0;
            }
        });
        for (Lord lord : list) {
            System.out.println(lord);
        }
    }

    static class Lord {
        private int ranks;
        private int level;// 当前等级
        private long fight;// 战斗力
        private String name;

        public Lord(String name, int ranks, int level, long fight) {
            this.ranks = ranks;
            this.level = level;
            this.fight = fight;
            this.name = name;
        }

        public int getRanks() {
            return ranks;
        }

        public void setRanks(int ranks) {
            this.ranks = ranks;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public long getFight() {
            return fight;
        }

        public void setFight(long fight) {
            this.fight = fight;
        }

        @Override
        public String toString() {
            return "Lord{" + "ranks=" + ranks + ", level=" + level + ", fight=" + fight + ", name='" + name + '\''
                    + '}';
        }
    }
}
