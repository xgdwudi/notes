package com.atguigu.test;

import com.atguigu.service.TAccountService;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName JTest4
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/7 18:35
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:bean1.xml")
public class JTest4 {
    @Autowired
    private TAccountService tAccountService;

    @Test
    public void adds(){
        tAccountService.asd();
    }
}
