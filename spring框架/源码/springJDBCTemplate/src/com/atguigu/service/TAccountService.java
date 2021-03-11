package com.atguigu.service;

import com.atguigu.dao.TAccountDao;
import com.atguigu.entity.TAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName TAccountService
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 21:54
 **/
@Service
public class TAccountService {

    @Autowired
    private TAccountDao tAccountDao;

    @Transactional
    public void update(){
        TAccount tAccount = new TAccount();
        tAccount.setMoney(100);
        tAccount.setName("lucy");
        tAccountDao.update(tAccount);
        int i = 1/0;
        TAccount tAccounts = new TAccount();
        tAccounts.setMoney(-100);
        tAccounts.setName("mary");
        tAccountDao.update(tAccounts);
    }
    public void asd(){
        System.out.println("asdasd");
    }
}
