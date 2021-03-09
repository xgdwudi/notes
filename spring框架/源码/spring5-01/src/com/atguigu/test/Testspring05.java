package com.atguigu.test;

import com.atguigu.User;
import com.atguigu.dao.userSerive;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Testspring05
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/4 22:07
 **/
public class Testspring05 {

    @Test
    public void adds(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean2.xml");
        userSerive user = applicationContext.getBean("userServiuce", userSerive.class);

         user.adds();
    }

}
