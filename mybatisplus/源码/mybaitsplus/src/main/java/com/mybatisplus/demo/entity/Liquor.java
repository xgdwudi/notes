package com.mybatisplus.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Liquor {
    @TableId(type = IdType.ASSIGN_ID)
    public Long id;
    public String name;
    public Integer price;
}