package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yyh
 * @date 2020/3/11
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AlgoStatusEnum {
    NORMAL("使用",0),
    DELETE("停止",1);
    private String desc;
    private Integer status;
}
