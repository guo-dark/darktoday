package com.tanhua.sso.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class QuartzStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private QuartzScheduler quartzScheduler;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            quartzScheduler.startJob();
            System.out.println("*******quartz调度器启动*******");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
