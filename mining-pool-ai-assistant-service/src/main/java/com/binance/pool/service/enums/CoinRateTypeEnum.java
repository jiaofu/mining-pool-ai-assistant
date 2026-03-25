package com.binance.pool.service.enums;

import com.binance.pool.service.util.Constants;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Created by yyh on 2020/3/20
 */
@Getter
public enum CoinRateTypeEnum {
    PPS(0, Constants.DEFAULT_PPS_RATE),
    FPPS(1, Constants.DEFAULT_FPPS_RATE);

    private Integer type;

    private BigDecimal feeRate;

    CoinRateTypeEnum(int type, BigDecimal feeRate) {
        this.type = type;
        this.feeRate = feeRate;
    }

    public static CoinRateTypeEnum parse(Integer type) {
        for (CoinRateTypeEnum a : values()) {
            if (a.type.equals(type) ) {
                return a;
            }
        }
        return FPPS;
    }
}
