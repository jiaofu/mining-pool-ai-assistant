package com.binance.pool.service.util;

import java.math.BigDecimal;

public class HashString {
    public static String getStr(BigDecimal bigDecimal){
        if(bigDecimal == null){
            return null;
        }
       return bigDecimal.setScale(8,BigDecimal.ROUND_DOWN).toPlainString();
    }
    public static String getStr(Long biglong){
        if(biglong == null){
            return null;
        }
        return String.valueOf(biglong);
    }
}
