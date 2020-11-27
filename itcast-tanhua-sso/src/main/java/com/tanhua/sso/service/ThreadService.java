package com.tanhua.sso.service;

import com.tanhua.sso.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ThreadService {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Async("asyncServiceExecutor")
    public void sendMQ(User finalUser) {
        try {
            Map<String, Object> msg = new HashMap<>();
            msg.put("userId", finalUser.getId());
            msg.put("date", new Date());
            /*此处 convert ""*/
            rocketMQTemplate.convertAndSend("tanhua-sso",msg);
            Thread.sleep(5000);
            log.info("mq send success。。。。。");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}