package com.atguigu;


import com.atguigu.service.TAccountService;
import com.atguigu.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName myTest01
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 18:45
 **/
public class myTest01 {

    @Test
    public void test01(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean1.xml");
        TAccountService sserService = classPathXmlApplicationContext.getBean("TAccountService", TAccountService.class);
        sserService.update();
    }
//    @Test
//    public void test02(){
//        ApplicationContext classPathXmlApplicationContext = new AnnotationConfigApplicationContext(Txconfig.class);
//        TAccountService sserService = classPathXmlApplicationContext.getBean("TAccountService", TAccountService.class);
//        sserService.update();
//    }
}
