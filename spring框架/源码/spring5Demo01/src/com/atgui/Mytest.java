package com.atgui;

import com.atgui.Aopanno.User;
import com.atgui.aopxml.Book;
import com.atgui.config.SpringConfig;
import com.atgui.controller.UserController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Mytest
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 12:55
 **/
public class Mytest {
    
    @Test
    public void test01(){
       ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean2.xml");
        Book userController = classPathXmlApplicationContext.getBean("book", Book.class);
        userController.buy();
    }

    @Test
    public void test02(){
        ApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController userController = annotationConfigApplicationContext.getBean("userController", UserController.class);
        userController.test();
    }

    @Test
    public void asdasd()  {
        try {
            int i = 1/0;
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("asdasd");
    }

}
