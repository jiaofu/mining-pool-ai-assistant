package com.binance.pool.config.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/5/22
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AwsBtcComApiConfig {
    private String appId;
    private String appName;
    private String secretKey;
}
