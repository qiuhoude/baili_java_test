package com.houde.rank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I on 2017/11/15.
 */
public class RankTest {

    public static void main(String[] args) {
        List<SRank> sActAward = new ArrayList<>();
        sActAward.add(new SRank(1, 1, 1));
        sActAward.add(new SRank(2, 3, 2));
        sActAward.add(new SRank(4, 10, 3));
        sActAward.add(new SRank(11, 30, 4));
        sActAward.add(new SRank(31, 60, 5));
        sActAward.add(new SRank(61, 100, 6));
        sActAward.add(new SRank(101, 300, 7));
        sActAward.add(new SRank(301, 1000, 8));

        final int showSize = sActAward.size() + 1;
        final int rankSize = 11; // 排行榜的长度
        List<ActRank> rankList = new ArrayList<>();
        for (int i = 1; i <= rankSize; i++) {
            rankList.add(new ActRank(i));
        }
        ActRank myRank = null;

        SRank myAward = null; //自己档位
        if (myRank != null) {
            for (SRank saa : sActAward) {
                if (myRank.rank <= saa.end) {
                    myAward = saa;
                    break;
                }
            }
        }
        System.out.println("自己的档位:" + myAward);

        List<ActRank> showRankList = new ArrayList<>();// 客户端显示的排行
        if (rankSize <= showSize) {
            // 1.当排行榜人数小于显示个数时
            if (myAward != null) { // 没有档位说明压根没有加入排行
                showRankList.addAll(rankList);
            } else {
                showRankList.addAll(rankList);
                if (rankSize >= showSize) {// 移除最后一个
                    showRankList.remove(rankSize - 1);
                }
                showRankList.add(new ActRank(0));// 添加自己
            }
        } else {
            // 2. 排行榜人数足够显示时
            // 实际最多能显示的档位
            List<SRank> sRealActAward = sActAward.stream().filter(saa -> rankSize >= saa.start)
                    .collect(Collectors.toList());
            Set<Integer> rankingSet = new HashSet<>(); // 去重使用
            if (myAward != null) {
                // 3 当自己有档位时在排行榜中
                for (SRank saa : sRealActAward) {
                    if (saa.awardLv > myAward.awardLv) { // 在自己之前的档位显示档位的显示第一名
                        rankingSet.add(saa.start);
                    } else if (saa.awardLv < myAward.awardLv) {// 在自己之后的档位显示档位的显示最后一名
                        rankingSet.add(saa.end>rankSize?rankSize:saa.end);
                    } else {
                        rankingSet.add(myRank.rank);// 与自己档位相等显示自己名次
                    }
                }
                for (int i = 1; rankingSet.size() < showSize; i++) {
                    rankingSet.add(i);
                }
                rankingSet.stream().sorted(Comparator.comparingInt(i -> i))
                        .forEach(rank -> {
                            ActRank actRank = rankList.get(rank - 1);
                            actRank.rank = rank;
                            showRankList.add(actRank);
                        });
            } else {
                // 4 自己没有档位不再旁行榜
                for (SRank saa : sRealActAward) {
                    rankingSet.add(saa.end>rankSize?rankSize:saa.end);
                }
                for (int i = 1; rankingSet.size() < showSize - 1; i++) { // showSize-1要改自己留个位置
                    rankingSet.add(i);
                }
                rankingSet.stream().sorted(Comparator.comparingInt(i -> i))
                        .forEach(rank -> {
                            ActRank actRank = rankList.get(rank - 1);
                            actRank.rank = rank;
                            showRankList.add(actRank);
                        });
                showRankList.add(myRank != null ? myRank : new ActRank(0)); // 无档位在最后,无档位当可能有名次
            }
        }
        showRankList.stream().forEach(System.out::println);
    }
}
