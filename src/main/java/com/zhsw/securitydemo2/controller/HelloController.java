package com.zhsw.securitydemo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @Author: zhengliang
 * @Description: hello
 * @Date: 2019/12/11 14:18
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
