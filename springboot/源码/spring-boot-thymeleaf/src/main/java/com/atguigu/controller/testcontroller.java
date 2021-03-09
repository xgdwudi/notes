package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class testcontroller {

    @RequestMapping("/do")
    public String test(){
        return "hello, world";
    }

    @RequestMapping("/test")
    public String asdasd(){
        return "test";
    }





}
