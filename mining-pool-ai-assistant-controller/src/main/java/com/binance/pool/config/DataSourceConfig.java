package com.binance.pool.config;

import com.binance.pool.config.bean.AwsDataSourceConfig;
import com.binance.pool.config.bean.SecurityNameConfig;
import com.binance.pool.service.config.PoolDataConfig;
import com.binance.pool.service.util.Constants;
import com.binance.pool.util.SecurityManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe: DataSource Config
 */
@Configuration
@Slf4j
public class DataSourceConfig {

    @Autowired
    SecurityNameConfig securityNameConfig;

    /**
     * 配置数据源1
     * @return
     */
    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSource1() {

        AwsDataSourceConfig awsDataSourceConfig = SecurityManagerUtil.getAwsDataSourceConfig(
                securityNameConfig.getDb1().get(0),
                securityNameConfig.getDb1().get(1));
        DataSource ds = DataSourceBuilder.create()
                .username(awsDataSourceConfig.getUsername())
                .password(awsDataSourceConfig.getPassword())
                .url(awsDataSourceConfig.poolJdbcUrl())
                .driverClassName(Constants.MYSQL_DRIVER_CLASS_NAME)
                .build();
        return ds;
    }



    @Bean(name = "dbRead")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    @Primary
    public DataSource dataSource3() {
        AwsDataSourceConfig awsDataSourceConfig = SecurityManagerUtil.getAwsDataSourceConfig(
                securityNameConfig.getDbRead().get(0),
                securityNameConfig.getDbRead().get(1));
        if(StringUtils.isNotBlank(awsDataSourceConfig.getHost())){
            return DataSourceBuilder.create()
                    .username(awsDataSourceConfig.getUsername())
                    .password(awsDataSourceConfig.getPassword())
                    .url(awsDataSourceConfig.poolJdbcUrl())
                    .driverClassName(Constants.MYSQL_DRIVER_CLASS_NAME)
                    .build();
        }else{
            return DataSourceBuilder.create()
                    .driverClassName(Constants.MYSQL_DRIVER_CLASS_NAME)
                    .build();
        }
    }


    @Bean(name = "dynamicDS")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源,当没有指定的时候使用，可以当做主数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("db1", dataSource1());
        // 注册多数据源
        dsMap.put("dbRead", dataSource3());

        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }
}
