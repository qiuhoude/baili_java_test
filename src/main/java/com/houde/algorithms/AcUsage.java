package com.houde.algorithms;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Collection;

/**
 * @author qiukun
 * @date 2021/12/17
 */
public class AcUsage {
    public static void main(String[] args) {
        Trie trie = Trie.builder()
                .addKeyword("妈的")
                .addKeyword("白话佛法")
                .addKeyword("69式")
                .build();

        String contents = "妈的-我看他说话的语气，好屌啊 fuck gangcunxiushu 心灵法门“白话佛法”系列节目 @@@69式111";
        Collection<Emit> emits = trie.parseText(contents);

        if (!emits.isEmpty()) {
            char[] strChars = contents.toCharArray();
            for (Emit emit : emits) {
                for (int i = emit.getStart(); i <= emit.getEnd(); i++) {
                    strChars[i] = '*';
                }
            }
            System.out.println(new String(strChars));
        }


//        Trie trie = Trie.builder()
//                .ignoreOverlaps()
//                .addKeyword("hot")
//                .addKeyword("hot chocolate")
//                .build();
//        Collection<Emit> emits = trie.parseText("hot chocolate");
//        System.out.println(emits);
    }
}
