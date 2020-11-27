package com.itheima.springbootsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
 @RequestMapping("/admin")
public class AdminController {



        @GetMapping("/greeting")
        @PreAuthorize("hasRole('EMPLOYEE')")
        /*赋予一个角色　*/
        public String greeting() {
            return "Hello,World!";
        }

        @GetMapping("/login")
        public String login() {

            return "login sucess";
        }
    }

