package com.tanhua;

import com.tanhua.sso.quartz.QuartzScheduler;
import com.tanhua.sso.service.SmsService;
import com.tanhua.sso.utils.CronUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(classes = TanhuaApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestT {
    @Autowired
    private SmsService smsService;

    @Autowired
    QuartzScheduler quartzScheduler;
    /*测试quartZ*/
    @Test
    public void test1() {
        // Date date = new Date();
        // String time = CronUtil.formatDateByPattern(date, null);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-11-04 17:25:30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            quartzScheduler.add(1, CronUtil.getCron(date));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
