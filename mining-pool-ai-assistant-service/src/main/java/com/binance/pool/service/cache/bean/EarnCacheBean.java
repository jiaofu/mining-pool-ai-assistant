package com.binance.pool.service.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by yyh on 2020/4/28
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EarnCacheBean {
    //api数据为userName， 数据库数据为puid
    private String user;
    //earn
    private BigDecimal profit;
    private double reject;
    private String speed;
    //minerDay
    private long time;
}
