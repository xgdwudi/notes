package com.atgui.controller;

import com.atgui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName UserController
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 12:54
 **/
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    public void test(){
        userService.add();
    }
}
