package com.binance.pool.dao.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by yyh on 2020/3/21
 */
@AllArgsConstructor
@Getter
public enum UnionMinerCoinTableEnum {
    //与算法的关系不用在这里维护

    ELA("Elastos","ela_merge_mining",0,BigDecimal.ZERO),
    VCASH("VCASH","vcash_merge_mining",0,new BigDecimal("50"));
    private String coinName;
    private String tableName;
    /**-1不可用*/
    private Integer status;
    private BigDecimal blockReword;//如果是按块奖励的，
    public static UnionMinerCoinTableEnum parseEnum(String coinName){
        for (UnionMinerCoinTableEnum a : values()) {
            if (a.coinName.equals(coinName) ) {
                if(a.status<0){
                    return null;
                }
                return a;
            }
        }
        return null;
    }
    public static UnionMinerCoinTableEnum parseEnumByTable(String tableName){
        for (UnionMinerCoinTableEnum a : values()) {
            if (a.tableName.equals(tableName) ) {
                if(a.status<0){
                    return null;
                }
                return a;
            }
        }
        return null;
    }

}
