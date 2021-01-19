package com.futao.imooc.learn.dianpin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.futao.imooc.learn.dianpin.dao.UserMapper;
import com.futao.imooc.learn.dianpin.model.UserEntity;
import com.futao.imooc.learn.dianpin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ft
 * @date 2021/1/19
 */
@AutoConfigureAfter(ElasticsearchRestTemplate.class)
@Service
public class UserServiceImpl implements UserService, ApplicationRunner {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public UserEntity getById(Integer id) {
        return userMapper.selectById(id);
    }


    @Override
    public void save2Es() {
        List<UserEntity> userEntities = userMapper.selectList(Wrappers.<UserEntity>lambdaQuery());
        elasticsearchOperations.save(userEntities);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        elasticsearchOperations.delete(new CriteriaQuery(new Criteria("id").is(1)), UserEntity.class);
//        System.out.println(elasticsearchRestTemplate.delete("id", elasticsearchRestTemplate.getIndexCoordinatesFor(UserEntity.class)));
        this.save2Es();
        System.out.println(elasticsearchOperations.get("1", UserEntity.class));
    }
}
