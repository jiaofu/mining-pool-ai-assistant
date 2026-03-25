package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yyh on 2020/4/23
 */
@Getter
@AllArgsConstructor
public enum ApiBtcComRegionEnum {
    API_CHINA_SOUTH(DataSourceConfigEnum.API_CHINA_SOUTH.getRegionName(),"https://binance.pool.btc.com"),
    API_CHINA_NORTH(DataSourceConfigEnum.API_CHINA_NORTH.getRegionName(),"https://binance.pool.btc.com"),
//    USA("无效","https://binance.pool.btc.com"),
    API_EUROPE(DataSourceConfigEnum.API_EUROPE.getRegionName(),"https://binance.pool.btc.com");
    private String regionName;
    private String url;
    public static ApiBtcComRegionEnum parseEnum(String regionName){
        for (ApiBtcComRegionEnum a : values()) {
            if (a.regionName.equals(regionName) ) {
                return a;
            }
        }
        return API_CHINA_SOUTH;
    }

}
