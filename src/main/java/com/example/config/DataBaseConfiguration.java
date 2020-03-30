package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: DataBaseConfiguration
 * @description: 数据源配置
 * @author: Allen
 * @create: 2020-02-25 14:46
 **/
@Configuration
@Slf4j
@Data
@ConfigurationProperties(prefix = "spring")
public class DataBaseConfiguration {


    @Value("com.alibaba.druid.pool.DruidDataSource")
    private Class<? extends DataSource> dataSourceType;

    private List<com.alibaba.druid.pool.DruidDataSource> slaves = new ArrayList<>();


    @Bean(name = "writeDataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource writeDataSource() {
        log.info("-----------------------writeDatSource init----------------------");
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("readDataSources")
    public List<DataSource> readDataSources() {
        List<DataSource> dataSources = new ArrayList<>();
        if (slaves != null && !slaves.isEmpty()) {
            slaves.forEach(item -> dataSources.add(item));
        }
        return dataSources;
    }


}
