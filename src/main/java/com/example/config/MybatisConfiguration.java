package com.example.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MybatisConfiguration
 * @description:
 * @author: Allen
 * @create: 2020-02-25 17:18
 **/
@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({DataBaseConfiguration.class})
public class MybatisConfiguration {

    /**
     * mybatis 配置路径
     */
    @Value("${mybatis.config-location:classpath:/mybatis.xml}")
    String configLocation;
    /**
     * mapper路径
     */
    @Value("${mybatis.mapperLocations:classpath:mapper/*.xml}")
    String mapperLocation;


    @Resource(name = "writeDataSource")
    private DataSource dataSource;

    @Resource(name = "readDataSources")
    private List<DataSource> readDataSources;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        PathMatchingResourcePatternResolver pr = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setConfigLocation(pr.getResource(configLocation));
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
        /** 添加mapper 扫描路径 */
        sqlSessionFactoryBean.setMapperLocations(pr.getResources(mapperLocation));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 有多少个数据源就要配置多少个bean
     *
     * @return
     */
    @Bean
    @ConditionalOnClass({EnableTransactionManagement.class})
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {

        int readSize = readDataSources == null ? 0 : readDataSources.size();
        DynamicDataSource proxy = new DynamicDataSource(readSize);

        Map<Object, Object> targetDataSources = new HashMap<>(10);
        targetDataSources.put(DataSourceType.WRITE.getType(), dataSource);
        for (int i = 0; readDataSources != null && i < readDataSources.size(); i++) {
            targetDataSources.put(i, readDataSources.get(i));
        }
        proxy.setDefaultTargetDataSource(dataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }


}
