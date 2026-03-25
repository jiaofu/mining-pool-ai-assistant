package com.binance.pool.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yyh on 2020/4/4
 */
@Data
@Component
@ConfigurationProperties(prefix = "pool.security.name")
public class SecurityNameConfig {
    /**业务库*/
    private List<String> db1;
    /**readOnly库*/
    private List<String> dbRead;

    /**南方节点*/
    private List<String> chinaSouth;
    /**北方节点*/
    private List<String> chinaNorth;
    /**美国节点*/
    private List<String> usa;
    /**欧洲节点*/
    private List<String> europe;
    /**pow-key*/
    private List<String> powKey;
    /**redis配置*/
    private List<String> redis;
    /**btcd rpc节点*/
    private List<String> btcd;

    /**btcCom 算力数据源apiConfig*/
    private List<String> btcComApiConfig;



    /**
     * db 的加盐密钥
     */
    private List<String> dbSecretkey;
}
