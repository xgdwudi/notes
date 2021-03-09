package com.atguigu.test;

import com.atguigu.service.TAccountService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName JTest5
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/7 18:52
 **/
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration("classpath:bean1.xml")
    @SpringJUnitConfig(locations = "classpath:bean1.xml")
public class JTest5 {

    @Autowired
    private TAccountService tAccountService;

    @Test
    public void adds(){
        tAccountService.asd();
    }
}
