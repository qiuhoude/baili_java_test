package com.houde.script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by I on 2017/9/15.
 */
public class ScriptTest {

    public static void main(String[] args) {
//        try {
//            script2();
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        System.out.println(new Date(0));
    }

    private static void script2() throws ScriptException, NoSuchMethodException, FileNotFoundException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        String path = ScriptTest.class.getResource("/").getPath();
        engine.eval(new FileReader(path+"/script.js"));

        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());

        invocable.invokeFunction("fun2", new Date());
        invocable.invokeFunction("fun2", LocalDateTime.now());
    }
    private static void script1() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            engine.eval("print('Hello World!');");
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    static String fun1(String name){
        System.out.format("Hi there from Java, %s", name);
        return "greetings from java";
    }
}
