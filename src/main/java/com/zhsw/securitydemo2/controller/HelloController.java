package com.zhsw.securitydemo2.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

/**
 * @Author: zhengliang
 * @Description: hello
 * @Date: 2019/12/11 14:18
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    @RolesAllowed("ONE")
    public String hello(){
        return "hello";
    }
}
