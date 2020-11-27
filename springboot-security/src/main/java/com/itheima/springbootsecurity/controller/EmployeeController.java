package com.itheima.springbootsecurity.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /*@PreAuthorize("123") prefilter:{ filterobject:{field value @prefilter(field:  vlaue:)
     @preauthorized(hasrole)returnobject.username=authorized.username
     enablewebsecurity() emabelgoable
     } }  */
    @GetMapping("/greeting")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    /*@PreAuthorize("principal.username.equals(#username)")*/
    @PreAuthorize("#admin.name.equals('abc')")
    /*方法调用完成后执行 accessdeniedexception */
    /*filterobject 表示集合中的当前对象 filtertarget代表过滤的参数 */
    @PreFilter(filterTarget = "ids",value = "filterObejct%2==0")
    @PostAuthorize("returnObject.id%2==0")
    public String greeting() {
        return "Hello,World!";
    }

    @GetMapping("/login")
    public String login() {

        return "login sucess";
    }



}
