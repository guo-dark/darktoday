package com.tanhua.sso.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class ScheduleJob implements Job {
    private void before(){
        System.out.println("before......");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        before();
        log.info(new Date()+".....");
        after();
    }

    private void after() {
        System.out.println("after....");
    }
}
