package com.xgd.spring.service;

import com.xgd.spring.spring.XgdApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @author wudidemiaoa
 * @date 2022/4/10
 * @apiNote
 */
public class Test {
    public static void main(String[] args) throws Exception {
        XgdApplicationContext bean = new XgdApplicationContext(AppConfig.class);
//        Object zhngsan = bean.getBean("test");
        System.out.println();
        System.out.println((UserInterFace) bean.getBean("userService"));
        System.out.println((UserInterFace) bean.getBean("userService"));
        System.out.println((OrderService) bean.getBean("orderService"));
        UserInterFace userService = (UserInterFace) bean.getBean("userService");
        userService.test();
    }
}
