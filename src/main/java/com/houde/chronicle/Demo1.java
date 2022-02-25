package com.houde.chronicle;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author qiukun
 * @date 2021/12/4
 */
public class Demo1 {


    public static void main(String[] args) throws IOException {
        ChronicleMap<Long, Map<Long, Player>> map =
                ChronicleMapBuilder
                        .of(Long.class, (Class<Map<Long, Player>>) (Class) Map.class)
                        .averageValueSize(1000)
                        .entries(50_000)
                        .createPersistedTo(new File("./test.dat"));


//        for (int i = 0; i < 50000; i++) {
//            Player player = new Player();
//            player.id = i;
//            player.data = new byte[10];
//            map.put(player.id, player);
//        }
//
//        map.forEach((k, v) -> {
//            System.out.println(v.id);
//        });
//        map.close();

    }

    public static class Player implements Serializable {
        public long id;
        public byte[] data;

        public Player() {
        }
    }
}
