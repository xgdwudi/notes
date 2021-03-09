package com.atguigu.dao;

import com.atguigu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName UserDao
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 18:29
 **/
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void adds(User user){
        Object[] args = {user.getName()};
        int update = jdbcTemplate.update("insert into user(names) values(?)", args);
        System.out.println(update);
    }
}
