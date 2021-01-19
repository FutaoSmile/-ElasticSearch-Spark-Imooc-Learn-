package com.futao.imooc.learn.dianpin.config;

import com.futao.imooc.learn.dianpin.util.TimeUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * ElasticSearch配置
 *
 * @author ft
 * @date 2021/1/19
 */
@Configuration
public class EsConfig extends AbstractElasticsearchConfiguration {

    @Autowired
    private ElasticsearchRestClientProperties elasticsearchRestClientProperties;

    @Bean(destroyMethod = "close")
    @Override
    public RestHighLevelClient elasticsearchClient() {
        return RestClients.create(ClientConfiguration.create(elasticsearchRestClientProperties.getUris().get(0))).rest();
    }

    /**
     * 转换器
     *
     * @return
     */
    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        ArrayList<Object> converters = new ArrayList<>(2);
        converters.add(LocalDateTimeConverter.INSTANCE);
        converters.add(LocalDateConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }

    /**
     * Long转LocalDateTime
     */
    @ReadingConverter
    enum LocalDateTimeConverter implements Converter<Long, LocalDateTime> {
        /**
         * singleton
         */
        INSTANCE;

        @Override
        public LocalDateTime convert(Long source) {
            return TimeUtils.longToLocalDateTime(source);
        }
    }

    /**
     * Long转LocalDate
     */
    @ReadingConverter
    enum LocalDateConverter implements Converter<Long, LocalDate> {
        /**
         * singleton
         */
        INSTANCE;

        @Override
        public LocalDate convert(Long source) {
            return TimeUtils.longToLocalDate(source);
        }
    }
}