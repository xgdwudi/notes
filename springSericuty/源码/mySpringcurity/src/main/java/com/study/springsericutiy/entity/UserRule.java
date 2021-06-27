package com.study.springsericutiy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_rule")
public class UserRule {
    /**
     * 唯一标识
     */
    private Object id;
    /**
     * 角色名称
     */
    private String rulename;
    /**
     * pid
     */
    private Object pid;
    /**
     *  rule
     */
    private String rule;

}
