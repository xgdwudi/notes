package com.atguigu.service;

import com.atguigu.dao.UserDao;
import com.atguigu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName UserService
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 18:30
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    
    public void adsds(String adss){
        User user = new User();
        user.setName(adss);
        userDao.adds(user);
    }
}
