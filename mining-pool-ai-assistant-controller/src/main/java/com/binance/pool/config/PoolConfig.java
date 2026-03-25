package com.binance.pool.config;

import com.binance.pool.config.bean.AwsBtcComApiConfig;
import com.binance.pool.config.bean.SecurityNameConfig;
import com.binance.pool.service.config.PoolDataConfig;
import com.binance.pool.service.config.SysConfig;
import com.binance.pool.util.SecurityManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yyh on 2020/4/5
 */
@Configuration
public class PoolConfig {
    @Autowired
    SecurityNameConfig securityNameConfig;

    /*@Bean
    @ConfigurationProperties(prefix = "pool")
    public SysConfig getSysConfig(){
        return SysConfig.builder()
                .prikey(SecurityManagerUtil.getAwsPowConfig(securityNameConfig.getPowKey().get(0),securityNameConfig.getPowKey().get(1)).getPrikey())
                .build();
    }*/

    @Bean
    @ConfigurationProperties(prefix = "pool")
    public SysConfig getSysConfig(){
        return SysConfig.builder()
                .dbSecretkey(SecurityManagerUtil.getUserConfig(securityNameConfig.getDbSecretkey().get(0),securityNameConfig.getDbSecretkey().get(1)).getDbSecretkey())
                .build();
    }





}
