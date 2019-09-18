package com.houde.rank;

/**
 * Created by I on 2017/11/15.
 */
public class SRank {
    public int start;
    public int end;
    public int awardLv;

    public SRank(int start, int end, int awardLv) {
        this.start = start;
        this.end = end;
        this.awardLv = awardLv;

    }

    @Override
    public String toString() {
        return "RankTest{" +
                "start=" + start +
                ", end=" + end +
                ", awardLv=" + awardLv +
                '}';
    }

}
