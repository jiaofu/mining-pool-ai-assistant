package com.binance.pool.service.vo.userInfo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WaringHashVo {
    private int sendCount;
    private BigDecimal lastHashRate;
    private long lastValidNum;
    private long lasetSendTime;
}
