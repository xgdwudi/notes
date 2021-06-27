package com.study.springsericutiy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class Users implements Serializable {
    private static final long serialVersionUID = 277157755824916402L;
    /**
     * 唯一标识
     */
    private Object id;
    /**
     * 昵称
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
}
