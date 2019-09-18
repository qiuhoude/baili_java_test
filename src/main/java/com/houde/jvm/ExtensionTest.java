package com.houde.jvm;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @author qiukun
 * @create 2018-06-02 10:27
 **/
public class ExtensionTest {

    public static void main(String[] args) {
        File[] dirs = getExtDirs();
        for (int i = 0; i < dirs.length; i++) {
            System.out.println(dirs[i].getAbsoluteFile());
        }

    }
    //ExtClassLoader类中获取路径的代码
    private static File[] getExtDirs() {
        //加载<JAVA_HOME>/lib/ext目录中的类库
        String s = System.getProperty("java.ext.dirs");
        File[] dirs;
        if (s != null) {
            StringTokenizer st =
                    new StringTokenizer(s, File.pathSeparator);
            int count = st.countTokens();
            dirs = new File[count];
            for (int i = 0; i < count; i++) {
                dirs[i] = new File(st.nextToken());
            }
        } else {
            dirs = new File[0];
        }
        return dirs;
    }
}
