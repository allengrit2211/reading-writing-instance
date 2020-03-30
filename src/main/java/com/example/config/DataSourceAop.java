package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/***
 * 保证该AOP在@Transactional之前执行
 */
@Aspect
@Order(-1)
@Slf4j
@Component
public class DataSourceAop {
    @Before("execution(* com.example..mapper..*query*(..)) || execution(* com.example..mapper..*get*(..)) || execution(* com.example..mapper..*select*(..)) ")
    public void setReadDataSourceType() {
        log.info("dataSource change：Read");
        DataSourceContextHolder.read();
    }

    @Before("execution(* com.example..mapper..*insert*(..)) || execution(* com.example..mapper..*save*(..)) || execution(* com.example..mapper..*update*(..)) ")
    public void setWriteDataSourceType() {
        log.info("dataSource change：Write");
        DataSourceContextHolder.write();
    }



    @After("execution(* com.example..mapper..*.*(..))")
    public void remove(){
        DataSourceContextHolder.clearDB();
        log.info("dataSource change: clear");
    }
}
