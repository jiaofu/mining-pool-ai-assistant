package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yyh on 2020/4/27
 */
@Getter
@AllArgsConstructor
public enum DataSourceConfigEnum {
    CHINA_SOUTH("chinaSouth","binance"),
    CHINA_NORTH("chinaNorth","binance"),
    USA("usa","binance"),
    EUROPE("europe","binance"),

    API_CHINA_SOUTH("hz","btcComApi"),
    API_CHINA_NORTH("huhehaote","btcComApi"),
    API_EUROPE("eu","btcComApi"),
    ;
    private String regionName;
    private String dataType;

}
