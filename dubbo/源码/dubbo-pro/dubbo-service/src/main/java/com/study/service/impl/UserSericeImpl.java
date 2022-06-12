package com.study.service.impl;

import com.study.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wudidemiaoa
 * @date 2022/2/6
 * @apiNote
 */
@Service
public class UserSericeImpl implements UserService {
    public String sayHello() {
        return "say hello " + new Date();
    }
}
