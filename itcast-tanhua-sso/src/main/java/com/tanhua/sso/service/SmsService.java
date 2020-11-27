package com.tanhua.sso.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanhua.sso.utils.SmsAliyun;
import com.tanhua.sso.utils.ValidateCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {
      /*resttemplate springboot 自带*/
    @Autowired/*加上泛型之后 存储数据 bgwriteof */
    private RedisTemplate<String,String>  redisTemplate;

    private static final ObjectMapper MAPPER = new ObjectMapper();


    public Map<String, Object> sendCheckCode(String phone) {
        HashMap map = new HashMap();
        try {

            //SmsAliyun.sendShortMessage(phone,String.valueOf(sendCode));
            /*将验证码 存入redis中 登陆使用 */
            String sendCode =String.valueOf( ValidateCodeUtils.generateValidateCode(6));
            System.out.println(sendCode);
       /*     String code = (String) redisTemplate.opsForValue().get("SEND_PHONE" + phone);
            if(!StringUtils.isEmpty(code)){
                map.put("code",1);
                map.put("msg", "上一次的验证码存在");
                return  map;
            }*/
            redisTemplate.opsForValue().set("SEND_PHONE"+phone,sendCode, Duration.ofSeconds(10000000));
            map.put("code", 3);
            map.put("msg","ok");
            return  map;
        } catch (Exception e) {
            e.printStackTrace();
           map.put("code",4);
           map.put("msg","验证码发送异常");
        }
        return map;
    }
}
