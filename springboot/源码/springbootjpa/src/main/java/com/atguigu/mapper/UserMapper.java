package com.atguigu.mapper;

import com.atguigu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName UserMapper
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/4 20:49
 **/
public interface UserMapper extends JpaRepository<User,Integer> {

}
