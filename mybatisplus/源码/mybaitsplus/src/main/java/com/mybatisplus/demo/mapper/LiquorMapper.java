package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.entity.Liquor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LiquorMapper extends BaseMapper<Liquor> {
//    @Param(Constants.WRAPPER) Wrapper<Liquor> queryWrapper
    @Select("select * from Liquor where name ilike CONCAT('%',#{liquor.name},'%')")
    IPage<Liquor> selectUserPage(Page<Liquor> page,@Param("liquor") Liquor liquor);

}