package com.atguigu.dao;

import org.junit.Test;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName userSerive
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/5 10:01
 **/
public class userSerive {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void adds(){
        userDao.adds();
    }
}
