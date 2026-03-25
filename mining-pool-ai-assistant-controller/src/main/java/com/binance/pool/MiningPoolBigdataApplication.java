package com.binance.pool;

import com.binance.pool.service.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
@MapperScan("com.binance.pool.*.mapper")
public class MiningPoolBigdataApplication {

    public static void main(String[] args) {

         OkHttpUtils.init();

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(MiningPoolBigdataApplication.class, args);

        log.info("bigdata-api actuator 3 restart qa test info aws sdk jdk:{} name:{} vendor:{} 4", System.getProperty("java.version"),System.getProperty("java.runtime.name"),System.getProperty("java.vendor"));
        log.error("bigdata-api actuator 3 restart app test error log aws sdk");
    }

}
