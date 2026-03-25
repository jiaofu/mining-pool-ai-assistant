package com.binance.pool.util;

import com.alibaba.fastjson.JSON;
//import com.amazonaws.services.secretsmanager.AWSSecretsManager;
//import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.binance.pool.config.bean.AwsBtcComApiConfig;
import com.binance.pool.config.bean.AwsDataSourceConfig;
import com.binance.pool.config.bean.UserMiningConfig;
import com.binance.pool.config.bean.AwsRedisConfig;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.Map;

/**
 * 读取AWSSecretsManager 上的数据库配置
 * Created by yyh on 2020/4/3
 */
@Slf4j
public class SecurityManagerUtil {
    private static final Map<String, SecretsManagerClient> smClientMap = Maps.newConcurrentMap();

    private static final String sslPassKey = "javax.net.ssl.trustStorePassword";
    private static final String sslStoreKey = "javax.net.ssl.trustStore";

    public static AwsDataSourceConfig getAwsDataSourceConfig(String secretName, String region) {

        try {
            String secret = doGetSecretString(secretName, region);
            return JSON.parseObject(secret, AwsDataSourceConfig.class);
        } catch (Exception e) {
            log.error("getAwsDataSourceConfig found an error:", e);
        }
        log.error("aws sm value not get {} {} secret, please double check.",secretName,region);
        return AwsDataSourceConfig.builder().build();
    }

    public static AwsRedisConfig getRedisConfig(String secretName, String region){

//        return AwsRedisConfig.builder().host("bp-beta-api-redis.wnnwba.ng.0001.apne1.cache.amazonaws.com").port(6379).build();
//
        try {
            String secret = doGetSecretString(secretName, region);
            return JSON.parseObject(secret, AwsRedisConfig.class);
        } catch (Exception e) {
            log.error("AwsRedisConfig found an error:", e);
        }
        log.error("aws sm value not get {} {} secret, please double check.",secretName,region);
        return AwsRedisConfig.builder().port(6379).build();
    }



    public static UserMiningConfig getUserConfig(String secretName, String region) {

        try {
            String secret = doGetSecretString(secretName, region);
            return JSON.parseObject(secret, UserMiningConfig.class);
        } catch (Exception e) {
            log.error("AwsRedisConfig found an error:", e);
        }
        log.error("aws sm value not get {} {} secret, please double check.",secretName,region);
        return UserMiningConfig.builder().build();
    }



    public static synchronized String doGetSecretString(String secretName,String region){
        String sslPass = System.getProperty(sslPassKey);
        String sslStore = System.getProperty(sslStoreKey);
        //mysql ssl证书会影响aws https请求，要先清除掉，获取完数据后再加上证书配置，保证mysql连接
        if(StringUtils.isNotBlank(sslPass)){
            System.clearProperty(sslPassKey);
            System.clearProperty(sslStoreKey);
        }
        try{

            SecretsManagerClient client = smClientMap.get(region);
            if(client == null){
                smClientMap.putIfAbsent(region,
                        SecretsManagerClient.builder().region(Region.of(region)).build()
                );
                client= smClientMap.get(region);
            }
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId(secretName).build();
            GetSecretValueResponse response = client.getSecretValue(request);

            if(response != null && StringUtils.isNotBlank(response.secretString())){
                return response.secretString();
            }

        }catch (Exception e){
            log.error("AWSSecretsManager found an secretName:{} region:{} error:",secretName,region,e);
            throw e;
        }finally {
            //加上证书配置，保证mysql连接
            if(StringUtils.isNotBlank(sslPass)){
                System.setProperty(sslPassKey,sslPass);
                System.setProperty(sslStoreKey,sslStore);
            }
//            log.info("reset sslPass and Store ok.");
        }
        return null;
    }

}
