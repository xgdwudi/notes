package com.atguigu.controller;

import com.atguigu.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Usercontroller
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/4 20:54
 **/
@RestController
public class Usercontroller {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/do")
    public Object queryAll(){
        return userMapper.findAll();
    }
}
