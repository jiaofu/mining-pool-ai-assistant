package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PoolUserPaymentEnum {
    INITIAL("初始值",0),
    CALL("调用接口",1),
    SUCCESS("成功",2);
    private String desc;
    private Integer status;

}
