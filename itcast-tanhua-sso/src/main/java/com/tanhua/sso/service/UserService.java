package com.tanhua.sso.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanhua.sso.mapper.UserMapper;
import com.tanhua.sso.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service

public class UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserMapper userMapper;
    @Value("${jwt.secret}")/*颜值*/
    private String secret;
    @Autowired
    private ThreadService threadService;

    public String login(String phone, String code) {

        String checkCode = redisTemplate.opsForValue().get("SEND_PHONE" + phone);
        log.info("{}"+checkCode+".."+code);

        if (checkCode == null || !checkCode.equals(code)){
            log.info("redis code not match....,redisCode:{},code:{}",checkCode,code);
            return null;
        }
        Boolean isNew = false;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone).last("limit 1");
        User selectUser = userMapper.selectOne(wrapper);
        System.out.println(selectUser);
        if (selectUser == null) {
            /*防空指针 异常 */
            selectUser=new User();
            selectUser.setMobile(phone);
            selectUser.setPassword(DigestUtils.md5Hex(secret + "_123456"));
            this.userMapper.insert(selectUser);
            isNew = true;
            log.info("register success..{}"+ selectUser.getId());
        }
        Map<String, Object> claims = new HashMap<String, Object>();
        /*保证token的唯一性 */
        claims.put("mobile", phone);
        claims.put("id", selectUser.getId());

        // 生成token
        String token = Jwts.builder()
                .setClaims(claims) //设置响应数据体 json格式自动转化 jwt的build方式
                .signWith(SignatureAlgorithm.HS256, secret) //设置加密方法和加密盐
                .compact();
        try {
            this.redisTemplate.opsForValue().set("TOKEN_" + token, mapper.writeValueAsString(selectUser), Duration.ofDays(111));
            System.out.println("dsfdfffsf");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
            /*发生异常之后 返回一个空值 */
        }
            User finalUser=selectUser;
            log.info("select user is  {}"+finalUser);
        try {
            threadService.sendMQ(finalUser);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return isNew + "|" + token;
    }
}
