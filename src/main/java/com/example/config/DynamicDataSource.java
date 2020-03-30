package com.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Random;

/**
 * @ClassName: MyAbstractRoutingDataSource
 * @description: 数据源切换
 * @author: Allen
 * @create: 2020-02-25 15:24
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    private int readSize;

    public DynamicDataSource(int readSize) {
        this.readSize = readSize;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (readSize == 0) {
            return DataSourceType.WRITE.getType();
        }

        String typeKey = DataSourceContextHolder.getDataSourceType();
        if (typeKey == null || typeKey.equalsIgnoreCase(DataSourceType.WRITE.getType())) {
            return DataSourceType.WRITE.getType();
        }

        return new Random().nextInt(readSize);

    }

}
