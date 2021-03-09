package com.atgui.Aopanno;

import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName User
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 16:58
 **/
@Component
public class User {
    public void add(){
//        int i = 1/0;
        System.out.println("add");
    }
}
