package com.tanhua;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tanhua.sso.mapper")
public class TanhuaApp {
    public static void main(String[] args) {
        SpringApplication.run(TanhuaApp.class, args);
    }

}
