package com.houde.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by I on 2017/11/23.
 */
public class SimpleJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleTrigger trigger = (SimpleTrigger) context.getTrigger();
        int cnt = trigger.getTimesTriggered();
        System.out.println("执行了第 ：" + cnt);
//        // job 的名字
//        String jobName = context.getJobDetail().getKey().getName();
//
//        // 任务执行的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");
        String jobRunTime = dateFormat.format(Calendar.getInstance().getTime());
        System.out.println(jobRunTime);
//
//        // 输出任务执行情况
//        System.out.println("任务 : " + jobName + " 在  " + jobRunTime + " 执行了 threadId:" + Thread.currentThread().getId());

    }
}
