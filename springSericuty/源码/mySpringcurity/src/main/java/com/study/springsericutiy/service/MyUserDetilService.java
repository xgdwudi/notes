package com.study.springsericutiy.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.springsericutiy.entity.Users;
import com.study.springsericutiy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetilService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //调用userMapper方法查询数据库
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        Users users = userMapper.selectOne(wrapper);
//        判断
        if(users==null){
            throw new UsernameNotFoundException("用户不存在！！");
        }
        List<GrantedAuthority> admin =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
//        从数据库返回user对象，得到用户名和密码
        return new User(users.getPhone(),
                new BCryptPasswordEncoder().encode(users.getPassword()),admin);
    }
}
