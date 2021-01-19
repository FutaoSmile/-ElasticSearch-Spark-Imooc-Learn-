package com.futao.imooc.learn.dianpin.service;

import com.futao.imooc.learn.dianpin.model.UserEntity;

/**
 * @author ft
 * @date 2021/1/19
 */
public interface UserService {
    /**
     * 通过ID查询用户
     *
     * @param id ID
     * @return 用户
     */
    UserEntity getById(Integer id);

    void save2Es();
}
