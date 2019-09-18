package com.houde.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by I on 2017/11/23.
 */
public class SimpleTriggerExample {
    public static void main(String[] args) throws Exception {
        SimpleTriggerExample example = new SimpleTriggerExample();
        example.run();

    }


    public void run() throws Exception {
        // 日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        sched.start();
        System.out.println("--------------- 初始化 -------------------");

        // 下一个第15秒 例:
        //           当前 10秒,则 执行时间为15秒
        //           当前 16秒,则 执行时间为30秒
        //           当前 33秒,则 执行时间为45秒
        //           当前 48秒,则 执行时间为00秒
//        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

//        Date startTime = dateFormat.parse("2017-12-01 11:21:00");

//        System.out.println(dateFormat.format(startTime));
        Date startTime2 = dateFormat.parse("2018-05-17 12:11:00");
        System.out.println(dateFormat.format(startTime2));

        addJob(sched, "job2", "group1", SimpleJob.class, startTime2);

//        // job1 将只会执行一次
//        JobDetail job1 = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("job1", "group1")
//                .forJob(job1)
//                .startAt(startTime)
//                .build();
//        // 把job1 和 trigger加入计划   .  ft:此任务要执行的时间
//        Date ft = sched.scheduleJob(job1, trigger);
////        System.out.println(job1.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
////                + trigger.getRepeatCount() + " 次, 每次间隔 "
////                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//
//        JobDetail job2 = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();
//        SimpleTrigger trigger2 = (SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("job2", "group1")
//                .startAt(startTime2)
//                .build();
//        sched.scheduleJob(job2, trigger2);

//        addJob(sched, "job1", "group1", SimpleJob.class, startTime);


//        // job2 将只会和执行一次(和job1一样一样的,吼~~)
//        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();
//        trigger = (SimpleTrigger) newTrigger()
//                .withIdentity("trigger2", "group1").startAt(startTime).build();
//        ft = sched.scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//        // job3 将执行11次(执行1次,重复10次) ,每10秒重复一次
//        job = newJob(SimpleJob.class).withIdentity("job3", "group1").build();
//        trigger = newTrigger()
//                .withIdentity("trigger3", "group1")
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule()
//                                .withIntervalInSeconds(10)// 重复间隔
//                                .withRepeatCount(10))     // 重复次数
//                .build();
//        ft = sched.scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//
//        // trigger3 改变了.  每隔10s重复.共重复2次
//        // 此处说明 , 上面job3已经 设定了 trigger3 重复10次,每次10s
//        //        在这里又改变了 trigger3的设置,不会对以前构成影响,而是当做一个新的来处理
//        trigger = newTrigger()
//                .withIdentity("trigger3", "group2")
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule()
//                                .withIntervalInSeconds(10)
//                                .withRepeatCount(1))
//                .forJob(job).build();
//        ft = sched.scheduleJob(trigger);
//        System.out.println(job.getKey().getName() + " 改变过trigger3属性的job3 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//        // job5 将在5分钟后运行一次
//        job = newJob(SimpleJob.class).withIdentity("job5", "group1").build();
//        trigger = (SimpleTrigger) newTrigger()
//                .withIdentity("trigger5", "group1")
//                .startAt(futureDate(5, IntervalUnit.MINUTE)) // 设定5分钟后运行
//                .build();
//        ft = sched.scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//        // job6  每40s运行一次,没有指定重复次数,则无下限的重复
//        job = newJob(SimpleJob.class).withIdentity("job6", "group1").build();
//        trigger = newTrigger()
//                .withIdentity("trigger6", "group1")
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule().withIntervalInSeconds(40)
//                                .repeatForever()).build();
//        ft = sched.scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//        // 所有的任务都被加入到了 scheduler中 ,但只有 schedulers.start(); 时才开始执行
//        sched.start();
//        System.out.println("------- 开始调度 (调用.start()方法) ----------------");
//        System.out.println("-------系统 启动 的 时间 :" + dateFormat.format(new Date()));
//
//        // 在 scheduled.start(); 之后,还可以将 jobs 添加到执行计划中
//        // job7 将重复20次 ,每5分钟重复一次
//        job = newJob(SimpleJob.class).withIdentity("job7", "group1").build();
//        trigger = newTrigger()
//                .withIdentity("trigger7", "group1")
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule()
//                                .withIntervalInMinutes(5) // 5分钟
//                                .withRepeatCount(20))     // 重复20次
//                .build();
//        ft = sched.scheduleJob(job, trigger);
//        System.out.println(job.getKey().getName() + " 将在 : " + dateFormat.format(ft) + " 时运行.并且重复: "
//                + trigger.getRepeatCount() + " 次, 每次间隔 "
//                + trigger.getRepeatInterval() / 1000 + " 秒");
//
//        // job8  可以立即执行. 无trigger注册
//        job = newJob(SimpleJob.class).withIdentity("job8", "group1")
//                .storeDurably().build();
//        sched.addJob(job, true);
//        System.out.println("手动触发  job8...(立即执行)");
//        sched.triggerJob(JobKey.jobKey("job8", "group1"));
//
//        System.out.println("------- 等待30 秒... --------------");
//
//        try {
//            Thread.sleep(30L * 1000L);
//        } catch (Exception e) {
//        }
//
//        // job7 将马上执行,重复10次,每秒一次
//        System.out.println("-------  重新安排 ... --------------------");
//        trigger = newTrigger()
//                .withIdentity("trigger7", "group1")
//                .startAt(startTime)
//                .withSchedule(
//                        simpleSchedule().withIntervalInMinutes(5)
//                                .withRepeatCount(20)).build();
//
//        ft = sched.rescheduleJob(trigger.getKey(), trigger);
//        System.out.println("job7 被重新安排 在 : " + dateFormat.format(ft) + "  执行. \r   当前时间 :" + dateFormat.format(new Date()) + "预定执行时间已过,任务立即执行");
//
//        try {
//            System.out.println("------- 等待5分钟  ... ------------");
//            Thread.sleep(300L * 1000L);
//        } catch (Exception e) {
//        }
//
//        sched.shutdown(true);
//        System.out.println("------- 调度已关闭 ---------------------");
//
//        // 显示一下  已经执行的任务信息
//        SchedulerMetaData metaData = sched.getMetaData();
//        System.out.println("~~~~~~~~~~  执行了 " + metaData.getNumberOfJobsExecuted() + " 个 jobs.");

    }

    /**
     * 移除定时器
     *
     * @param sched
     * @param jobName
     * @param jobGroupName
     */
    public static void removeJob(Scheduler sched, String jobName, String jobGroupName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            Trigger trigger = sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            sched.pauseTrigger(triggerKey);
            sched.unscheduleJob(triggerKey);
            sched.deleteJob(jobKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 只会执行一次
     *
     * @param sched
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param startAt      开始执行的时间
     */
    public static void addJob(Scheduler sched, String jobName, String jobGroupName, Class<? extends Job> jobClass,
                              Date startAt) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startAt = dateFormat.parse("2018-05-17 12:29:00");
        Date endAt = dateFormat.parse("2018-06-17 12:30:00");


        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
        try {
            Trigger trigger = sched.getTrigger(triggerKey);
            if (trigger == null) {
                JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).build();
                SimpleTrigger st = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(triggerKey)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).startAt(startAt)
                        .endAt(endAt)
                        .build();
                sched.scheduleJob(jobDetail, st);
            } else {
//                Date startTime = trigger.getStartTime();
//                if (startTime.getTime() != startAt.getTime()) {
//                    SimpleTrigger st = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(triggerKey)
//                            .startAt(startAt).build();
//                    sched.rescheduleJob(triggerKey, st);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
