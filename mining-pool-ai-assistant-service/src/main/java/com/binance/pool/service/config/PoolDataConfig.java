package com.binance.pool.service.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by yyh on 2020/4/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolDataConfig {
    private String region;

    private String appId;
    private String appName;
    private String secretKey;


    /**
     * 请求的用户名
     */
    String userName;
    /**
     * 请求的密码
     */
    String password;
    /**
     * 请求的url
     */
    String multiUserUrl;


    /**
     * 美国节点
     */
    String multiUserUrlUs;

    /**
     * 杭州币种切换
     */
    String multiUserUrlHz;


    public String getBinanceDs(String currency) {
        if (StringUtils.equalsIgnoreCase(currency, "btc")) {
            return this.getRegion();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

}

