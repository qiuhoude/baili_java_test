package com.houde.btree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树节点
 */
public class BNode extends Node {

    BNode left;
    BNode right;
    BNode parent;
    int key;

    public BNode() {
    }

    public BNode(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key + "";
    }

    static {
        System.out.println("BNode load");
    }
}

class Node {
    static {
        System.out.println("Node load");
    }
}

/**
 * 二叉树
 */
class BTree {
    BNode root;

    public void add(int z) {
        add(new BNode(z));
    }

    public void add(BNode z) {
        BNode tmp = null;
        BNode p = root;
        while (p != null) {
            tmp = p;
            if (z.key < p.key) {
                p = p.left;
            } else if (z.key > p.key) {
                p = p.right;
            } else {
                // 添加相同的元素时
                return;
            }
        }
        z.parent = tmp;
        if (tmp == null) {
            root = z;
        } else if (z.key < tmp.key) {
            tmp.left = z;
        } else {
            tmp.right = z;
        }
    }

    public BNode find(int key) {
        BNode p = root;
        while (p != null) {
            if (p.key > key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    public void delete(int key) {
        BNode del = find(key);
        if (del == null) {
            // 删除的值不存在
            return;
        }
        if (del.right == null && del.left == null) {// 情况1 自己没有子节点的情况,父对应的子指向null
            if (del.key > del.parent.key) {// 自己在父的右边
                del.parent.right = null;
            } else if (del.key < del.parent.key) { // 左边
                del.parent.left = null;
            }
            del.parent = null;
        } else if (del.right == null || del.left == null) {// 情况2 只有一个子节点时,父结点的相应儿子指针指向儿子的独生子
            BNode noChildNull = del.right != null ? del.right : del.right;
            noChildNull.parent = del.parent;
            if (del.key > del.parent.key) {// 自己在父的右边
                del.parent.left = noChildNull;
            } else if (del.key < del.parent.key) { // 左边
                del.parent.right = noChildNull;
            }
            del.parent = null;
            del.left = null;
            del.right = null;
        } else {// 情况3 有两个子节点
                // 删除的原则 ,找到左儿子中最大 或右儿子最小的 取代自己的位置
            BNode mix = null;
            BNode p = del.right;
            while (p != null) {
                mix = p;
                p = p.left;
            }
            // 右边最小的节点的子节点
            mix.left = del.left;
            BNode mixParent = mix.parent;
            if (mix.right != null) {
                mixParent.left = mix.right;
                mixParent.left.parent=mixParent;
            }else{
                mixParent.left = null;
            }
            mix.right = del.right;
            mix.parent = del.parent;
            del.right.parent = mix;
            del.left.parent = mix;
            if(del.key>del.parent.key){
                del.parent.right = mix;
            }else {
                del.parent.left = mix;
            }
            del.parent = null;
            del.left = null;
            del.right = null;
        }
    }

    public void printTree2() {
        Queue<BNode> queue = new LinkedList<>();
        if (root == null) {
            System.out.println("一颗空树");
            return;
        }
        queue.add(root);

        while (!queue.isEmpty()) {
            Queue<BNode> q = queue;
            BNode n = q.poll();
            if (n != null) {
                String pos = n.parent == null ? "" : " myPos:" + (n == n.parent.left ? " LE" : " RI");
                String pstr = n.parent == null ? "" : " myParent:" + n.parent.toString();
                System.out.print(n + "(" + pstr + (pos) + ")" + "\n");
                if (n.left != null) {
                    q.add(n.left);
                }
                if (n.right != null) {
                    q.add(n.right);

                }
            }
        }
    }

    public void printTree() {
        Queue<BNode> queue = new LinkedList<>();
        Queue<BNode> queue2 = new LinkedList<>();
        if (root == null) {
            System.out.println("一颗空树");
            return;
        }
        queue.add(root);
        boolean firstQueue = true;
        while (!queue.isEmpty() || !queue2.isEmpty()) {
            Queue<BNode> q = firstQueue ? queue : queue2;
            BNode n = q.poll();
            if (n != null) {
                String pos = n.parent == null ? "" : " myPos:" + (n == n.parent.left ? " LE" : " RI");
                String pstr = n.parent == null ? "" : " myParent:" + n.parent.toString();
                System.out.print(n + "(" + pstr + (pos) + ")" + "\t");
                if (n.left != null) {
                    (firstQueue ? queue2 : queue).add(n.left);
                }
                if (n.right != null) {
                    (firstQueue ? queue2 : queue).add(n.right);
                }
            } else {
                System.out.println();
                firstQueue = !firstQueue;
            }

        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
         BTree t = new BTree();
         t.add(53);
         t.add(30);
         t.add(72);
         t.add(14);
         t.add(39);
         t.add(61);
         t.add(84);
         t.add(9);
         t.add(23);
         t.add(34);
         t.add(47);
         t.add(79);
         t.add(80);
         t.add(81);
         t.printTree2();
         t.delete(72);
         System.out.println("删除后");
         t.printTree2();
    }
}