package com.houde.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapHelper {

    public static Turple<Integer, Integer> reduceXYInArea(int areaId) {
        Turple<Integer, Integer> turple = new Turple<Integer, Integer>(((areaId - 1) % 5) * 100,
                (4 - (areaId - 1) / 5) * 100);
        return turple;
    }

    public static Turple<Integer, Integer> reducePos(int pos) {
        Turple<Integer, Integer> turple = new Turple<Integer, Integer>(pos % 500, pos / 500);
        return turple;
    }

    /**
     * 计算坐标所属的行政分区
     * 
     * @param pos
     * @return
     */
    public static int getAreaIdByPos(int pos) {
        if (pos < 0) {
            return -1;
        }
        Turple<Integer, Integer> xy = reducePos(pos);

        // 原来的算法
        // int xArea = (xy.getA() - 1) / 100 + 1;
        // int yArea = (xy.getB() - 1) / 100;
        //// return xArea + yArea * 5;
        // return AREA_MAP[(xArea + yArea * 5) - 1];

        int xArea = (xy.getA()) / 100 + 1;
        int yArea = (xy.getB()) / 100;
        // return xArea + yArea * 5;
        return AREA_MAP[(xArea + yArea * 5) - 1];

    }

    public static final int[] AREA_MAP = { 21, 22, 23, 24, 25, 16, 17, 18, 19, 20, 11, 12, 13, 14, 15, 6, 7, 8, 9, 10,
            1, 2, 3, 4, 5 };

    /**
     * 计算两个坐标点的距离，这里的距离不是直线距离，而是X、Y轴距离的和
     *
     * @param pos1
     * @param pos2
     * @return
     */
    public static int calcDistance(int pos1, int pos2) {
        Turple<Integer, Integer> turple1 = reducePos(pos1);
        Turple<Integer, Integer> turple2 = reducePos(pos2);

        return Math.abs(turple1.getA() - turple2.getA()) + Math.abs(turple1.getB() - turple2.getB());
    }

    public static List<Integer> getLineAcorss(int pos1, int pos2) {
        // 非跨区的情况
        List<Integer> areaList = new ArrayList<>();
        int area1 = MapHelper.getAreaIdByPos(pos1);
        int area2 = MapHelper.getAreaIdByPos(pos2);
        if (area1 == area2) {
            areaList.add(area1);
            return areaList;
        }

        areaList.add(area1);
        areaList.add(area2);
        // 跨区的情况
        Turple<Integer, Integer> xy1 = reducePos(pos1);
        Turple<Integer, Integer> xy2 = reducePos(pos2);
        // 斜率的计算
        int dx = xy1.getA() - xy2.getA();
        int dy = xy1.getB() - xy2.getB();
        if (dx == 0) { // 斜率不存在
            int minArea = Math.min(area1, area2);
            int maxArea = Math.max(area1, area2);
            for (int i = minArea + 1; i < maxArea; i += 5) {
                areaList.add(i);
            }
        } else if (dy == 0) {// 斜率为0
            int minArea = Math.min(area1, area2);
            int maxArea = Math.max(area1, area2);
            for (int i = minArea + 1; i < maxArea; i++) {
                areaList.add(i);
            }
        } else {
            // y = kx + b;
            double k = dy / dx;
            double b = (k * (xy1.getA() + xy2.getA()) - xy1.getB() - xy2.getB()) / 2;
            // x轴范围
            int minX = Math.min(xy1.getA(), xy2.getA());
            int maxX = Math.max(xy1.getA(), xy2.getA());
            int startX = minX / 100;
            int endX = maxX / 100;
            for (int i = startX; i <= endX; i += 100) {//
                int y = (int) (k * i + b);
                int pos = i + y * 500;
                int areaId = getAreaIdByPos(pos);
                areaList.add(areaId);
            }

            // y轴范围
            int minY = Math.min(xy1.getB(), xy2.getB());
            int maxY = Math.max(xy1.getB(), xy2.getB());
            int startY = minY / 100;
            int endY = maxY / 100;
            for (int i = startY; i <= endY; i += 100) {//
                // x = (y-b)/k
                int x = (int) ((i - b) / k);
                int pos = x + i * 500;
                int areaId = getAreaIdByPos(pos);
                areaList.add(areaId);
            }
        }
        // 去重
        return areaList.stream().distinct().collect(Collectors.toList());
    }

}
