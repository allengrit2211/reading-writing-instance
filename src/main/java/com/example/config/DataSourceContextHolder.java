package com.example.config;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DataSourceContextHolder
 * @description: 当前线程数据源
 * @author: Allen
 * @create: 2020-02-25 15:11
 **/
@Slf4j
public class DataSourceContextHolder {

    private DataSourceContextHolder() {
        throw new IllegalStateException("Utility class");
    }


    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();


    public static void read() {
        log.debug("read----");
        LOCAL.set(DataSourceType.READ.getType());
    }

    /***
     *
     */
    public static void write() {
        log.debug("write-----");
        LOCAL.set(DataSourceType.WRITE.getType());
    }

    public static void clearDB() {
        log.debug("remove-----");
        LOCAL.remove();
    }

    public static String getDataSourceType() {
        return LOCAL.get();
    }


}
