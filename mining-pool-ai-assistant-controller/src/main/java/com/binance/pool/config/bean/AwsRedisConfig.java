package com.binance.pool.config.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/4/20
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwsRedisConfig {
    @JSONField(name = "redis_pass")
    private String redisPass;
    private String host;
    private Integer port;
}
