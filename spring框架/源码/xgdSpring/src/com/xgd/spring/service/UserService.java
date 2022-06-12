package com.xgd.spring.service;

import com.xgd.spring.spring.*;

/**
 * @author wudidemiaoa
 * @date 2022/4/10
 * @apiNote
 */
@Component
@Scope("asdasfasdasd")
public class UserService implements UserInterFace{

    @Autowired
    private OrderService orderService;

    private String beanName;


    public void test(){
        System.out.println(orderService);
    }

}
