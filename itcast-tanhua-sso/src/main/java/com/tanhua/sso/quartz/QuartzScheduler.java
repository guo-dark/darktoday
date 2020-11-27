package com.tanhua.sso.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class QuartzScheduler {
    /*为业务层(eg: service)封装调用quartz的方法，该方法暴露执行的cron表达式（即第一步）
    将业务层需要执行的作业放到quartz的执行方法中（即第二步）
    springboot集成quartz，将quartz任务调度器跟随项目启动而启动起来（即第三步）*/

    /*设置 常量*/
    private static final String JOB_NAME = "inspect_report";
    private static final String JOB_GROUP = "inspect_report_group";
    private static final String TRIGGER_NAME = "inspect_report";
    private static final String TRIGGER_GROUP = "inspect_report_group";
    private static final String JOB_TASK_ID = "job_task_id";
    /*任务调度器 */
    @Autowired
    private Scheduler scheduler;
    public void startJob() throws SchedulerException {
        // 这里可以放一些初始化的任务，例如服务器宕机后，需要重新启动，如果没有不用考虑这个
        // 步骤：1.创建一个新的 SchedulerJob 作业类，即第二步的代码
        // 2.在这个类里写一个方法(invoke())调用新SchedulerJob的作业，然后将方法放到这里
        scheduler.start();
    }

    public void add(int i, String cron) throws SchedulerException {
        // 构建传递参数
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(JOB_TASK_ID, id);
        jobDataMap.put("userId", userId);

        JobDetail jobDetail = JobBuilder.newJob(InspectReportSchedulerJob.class).usingJobData(jobDataMap).

                withIdentity(JOB_NAME + id, JOB_GROUP).build();
        // 每5s执行一次
        // String cron = "*/5 * * * * ?";
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().
                withIdentity(TRIGGER_NAME + i, TRIGGER_GROUP).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public void remove(int i) throws SchedulerException {
        boolean deleteJob = scheduler.deleteJob(new JobKey(JOB_NAME + i, JOB_GROUP));
        log.info(deleteJob ? "任务移除成功" : "任务移除失败");
    }

    /**
     * 初始注入scheduler
     *初始化注入scheduler
     * @return scheduler
     * @throws SchedulerException SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }
}


