package com.futao.imooc.learn.dianpin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author ft
 * @date 2021/1/19
 */
@Getter
@Setter
@TableName("dianpin_user")
@Document(indexName = "es_spark_user", shards = 1, replicas = 0, createIndex = true)
@ToString(callSuper = true)
public class UserEntity extends IdTimeEntity {
    private String nickname;
    private String mobile;
    private int gender;
}
