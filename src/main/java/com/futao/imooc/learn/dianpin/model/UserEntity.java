package com.futao.imooc.learn.dianpin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.futao.imooc.learn.dianpin.model.constant.EsAnalyzeConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

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

    @Field(analyzer = EsAnalyzeConstants.IK_MAX_WORD, searchAnalyzer = EsAnalyzeConstants.IK_SMART)
    private String nickname;
    private String mobile;
    private int gender;
}
