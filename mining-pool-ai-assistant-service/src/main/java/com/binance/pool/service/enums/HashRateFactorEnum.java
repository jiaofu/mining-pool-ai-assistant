package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author yyh
 * @date 2020/3/10
 * 用于计算矿机算力
 * (hash/s) = (accepted_share) * (factor) / (s)
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum HashRateFactorEnum {
    BTCOIN_SHA256("Bitcoin","SHA256d",4294967296l),//2 ^ 32
    BTC_SHA256("btc","SHA256d",4294967296l),//2 ^ 32
    BCH_SHA256("BCH","SHA256d",4294967296l),
    UBTC_SHA256("UBTC","SHA256d",4294967296l),
    SBTC_SHA256("SBTC","SHA256d",4294967296l),
    ETH_Ethash("ETH","Ethash",1l),
    DCR_Blake256("DCR","Blake256",4294967296l),
    LTC_Scrypt("LTC","Scrypt",65536l),//2^16
    Grin_Cuckaroo29("Grin","Cuckaroo29",42l),
    Grin_Cuckatoo31("Grin","Cuckatoo31+",42l),
    ZCash_EquiHash("ZCash","EquiHash (n=144, k=5)",8192l),
    Beam_EquiHash("Beam","EquiHash (n=150, k=5)",1l)
    ;
    private String coinName;
    private String algoName;
    private long factor;

    public static long getFactor(String coinName){
        for (HashRateFactorEnum a : values()) {
            if (a.coinName.equalsIgnoreCase(coinName)) {
                return a.factor;
            }
        }
        return 0l;
    }

    /**
     * 计算算力
     * @param acceptedShare
     * @param factor
     * @param time 秒数
     * @return
     */
    public static long hashRate(long acceptedShare,long factor,long time){
        return hashRate(new BigDecimal(acceptedShare),factor,time).longValue();
    }
    public static BigDecimal hashRateBig(long acceptedShare,long factor,long time){
        return hashRate(new BigDecimal(acceptedShare),factor,time);
    }
    public static BigDecimal hashRate(BigDecimal acceptedShare, long factor, long time){
        BigDecimal hashRate = acceptedShare.multiply(new BigDecimal(factor)).divide(new BigDecimal(time), 0, RoundingMode.HALF_UP);
        return hashRate;
    }

    public static void main(String[] args) {
        System.out.println(hashRate(125829120l,4294967296l,60*60));
    }
}
