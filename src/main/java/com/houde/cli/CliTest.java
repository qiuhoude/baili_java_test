package com.houde.cli;

import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * @author qiukun
 * @create 2018-12-10 10:35
 **/
public class CliTest {
    //演示使用的是1.3的版本
    public static void main(String[] args) throws IOException, ParseException {
        //java wordcount [-help] [-O][value] [-c] <filename> otherfilename
        //option的容器
        Options options = new Options();
        //boolean型的option
        options.addOption("help", false, "help information");
        //当第二参数是true时，可以是这样的参数  -O4
        options.addOption("O", true, "you can set a value after the O");
        Option c = Option.builder("c")  //option的名字,判断的时候要使用这个名字
                .required(false)               //是否必须有这个选项
                .hasArg()                      //带一个参数
                .argName("filename")     //参数的名字
                .desc("return sum of characters")  //描述
                .build();                             //必须有
        //将c这个option添加进去
        options.addOption(c);

        //parser
        CommandLineParser parser = new DefaultParser();
//        String a = "java wordcount [-help] [-O][value] [-c] <filename> otherfilename";
//        String a = "-help";
        String a = "-c {'dsd', sds}   sdasd sda";
        args = a.split("//s+");
        CommandLine cmd = parser.parse(options, args);
        //询问是否有help
        if (cmd.hasOption("help")) {
            //调用默认的help函数打印
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java wordcount [OPTION] <FILENAME>", options);
            return;
        }

        if (cmd.hasOption("c")) {
            //获得相应的选项（c）的参数
            String filename = cmd.getOptionValue("c");
            System.out.println(filename);
            //do something
        }
        //将除了选项之外的参数打印出来 otherfilename
        String[] s = cmd.getArgs();
        for (String e : s) {
            System.out.println("=" + e);
        }

    }
}
