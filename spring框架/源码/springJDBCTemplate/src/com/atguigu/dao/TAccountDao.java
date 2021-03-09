package com.atguigu.dao;

import com.atguigu.entity.TAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName TAccountDao
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 21:55
 **/
@Repository
public class TAccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void update(TAccount tAccount){
        String sql = "update t_account set money=money+? where username = ?";
        Object[] args = {tAccount.getMoney(),tAccount.getName()};
        int update = jdbcTemplate.update(sql, args);
        System.out.println(update);
    }
}
