package com.study.springsericutiy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.springsericutiy.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {

}
