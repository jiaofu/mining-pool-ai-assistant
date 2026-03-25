package com.binance.pool.config.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/4/3
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwsDataSourceConfig {
    private String username;
    private String password;
    private String engine;
    private String host;
    private String port;
    private String dbInstanceIdentifier;

    /**
     * 获取pool库的连接
     * @return
     */
    public String poolJdbcUrl(){
        return String.format("jdbc:%s://%s:%s/pool?autoReconnect=true&useUnicode=true&useSSL=false&characterEncoding=utf-8",engine,host,port);
    }
    public String btcJdbcUrl(){
        return String.format("jdbc:%s://%s:%s/btc?autoReconnect=true&useUnicode=true&useSSL=false&characterEncoding=utf-8",engine,host,port);
    }
    public String btcSslJdbcUrl(){
        return String.format("jdbc:%s://%s:%s/btc?verifyServerCertificate=true&useSSL=true&requireSSL=true&autoReconnect=true&useUnicode=true&characterEncoding=utf-8",engine,host,port);
    }
}
