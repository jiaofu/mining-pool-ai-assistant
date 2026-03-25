package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yyh on 2020/3/17
 */
@AllArgsConstructor
@Getter
public enum PowPendingStatusEnum {

    RECORD("仅记录用，不是要发的账单",-1),
    PENDING("未处理",0),
    PROCESSED("已处理",1);
    private String desc;
    private Integer status;
    public static PowPendingStatusEnum parseEnum(Integer status){
        for (PowPendingStatusEnum a : values()) {
            if (a.status.equals(status) ) {
                return a;
            }
        }
        return null;
    }

}
