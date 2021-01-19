package com.futao.imooc.learn.dianpin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/**
 * @author ft
 * @date 2021/1/19
 */
@Getter
@Setter
@ToString
public class IdTimeEntity {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer createBy;

    //    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss", format = DateFormat.custom)
    private LocalDateTime createDateTime;

    private Integer updateBy;

    //    @Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss", format = DateFormat.custom)
    private LocalDateTime updateDateTime;
}
