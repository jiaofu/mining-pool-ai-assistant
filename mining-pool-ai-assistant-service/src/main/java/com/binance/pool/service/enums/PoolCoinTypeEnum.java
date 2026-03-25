package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by yyh on 2020/3/17
 */
@AllArgsConstructor
@Getter
public enum PoolCoinTypeEnum {
    MINER_COIN("挖矿币种",0),
    UNION_COIN("联合挖矿币种",1),
    REWORD_COIN("奖励挖矿币种",2);

    private String desc;
    private Integer type;
}
