package com.houde.algorithms;

import java.io.File;
import java.util.Stack;

/**
 * Created by I on 2017/12/11.
 */
public class FileTest {

    public static void main(String[] args) {
        //递归显示C盘下所有文件夹及其中文件
        File root = new File("H:");
//        findAllFilesRecursion(root);
        findAllFilesNoRecursion(root);
    }

    static void findAllFilesNoRecursion(File root) {
        Stack<File> stack = new Stack<>();
        int cnt = 0;
        for (File f : root.listFiles()) {
            if (f.isDirectory()) {
                stack.push(f);
            } else {
                System.out.println(f.getAbsolutePath());
                cnt++;
            }
            while (!stack.isEmpty()) {
                File popFile = stack.pop();
                System.out.println(popFile.getAbsolutePath()); //文件夹打印
                cnt++;
                if (popFile.isDirectory()) {
                    File[] files = popFile.listFiles();
                    if (files != null) {
                        for (File f2 : files) {
                            if (f2.isDirectory()) {
                                stack.push(f2);
                            } else {
                                System.out.println(f2.getAbsolutePath());
                                cnt++;
                            }
                        }
                    }
                } else {
                    System.out.println(popFile.getAbsolutePath());
                    cnt++;
                }
            }
        }
        System.out.println("一共 "+cnt);
    }

    final static void findAllFilesRecursion(File root) {
        long start = System.currentTimeMillis();
        try {
            showAllFiles(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("递归执行:" + (end - start));
    }

    final static void showAllFiles(File dir) throws Exception {
        File[] fs = dir.listFiles();
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fs[i].getAbsolutePath());
            if (fs[i].isDirectory()) {
                try {
                    showAllFiles(fs[i]);
                } catch (Exception e) {
                }
            }
        }
    }
}
