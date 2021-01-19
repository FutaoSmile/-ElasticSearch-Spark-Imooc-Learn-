package com.futao.imooc.learn.dianpin;

import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author futao
 * @date 2021/01/19
 */
@MapperScan("com.futao.imooc.learn.dianpin.dao")
@SpringBootApplication
public class LearnDianpinApplication implements ApplicationRunner {

    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(LearnDianpinApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
