package com.futao.imooc.learn.dianpin.controller;

import com.futao.imooc.learn.dianpin.dao.UserMapper;
import com.futao.imooc.learn.dianpin.model.UserEntity;
import com.futao.imooc.learn.dianpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ft
 * @date 2021/1/19
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public UserEntity getById(@RequestParam Integer id) {
        return userService.getById(id);
    }

    @GetMapping
    public String hello() {
        return "hello";
    }
}
