package com.houde;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;

/**
 * @author qiukun
 * @create 2018-05-24 19:09
 **/
public class JavasisisUtil {

    public static void main(String[] args) {
        ClassPool pool = ClassPool.getDefault();
        try {
            //取得需要反编译的jar文件，设定路径
            pool.insertClassPath("f://libammsdk.jar");

            //取得需要反编译修改的文件，注意是完整路径（注意：因为代码在内部类中，所以我读取的是WebViewActivity$2文件
            CtClass cc1 = pool.get("com.tencent.mm.opensdk.a.a");

            //取得需要修改的方法
            CtMethod method = cc1.getDeclaredMethod("a");

            //插入修改项，我们让他直接返回(注意：根据方法的具体返回值返回，因为这个方法返回值是void，所以直接return；)
            method.instrument(
                    new ExprEditor() {
                        public void edit(MethodCall m)
                                throws CannotCompileException {

                            System.out.println(m.getClassName() + ", " + m.getMethodName());
                            // 在这里搜索class中符合条件的逻辑代码，并替换成我想改的
//                            if ( m.getClassName().equals("java.lang.String")
//                                    && m.getMethodName().equals("方法名")) {
//                                m.replace(
//                                        "$_ = $proceed(\"https\") ;");
//                            }
                        }
                    });

            //写入保存
            cc1.writeFile();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
