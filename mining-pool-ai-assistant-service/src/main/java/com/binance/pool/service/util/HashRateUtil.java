package com.binance.pool.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HashRateUtil {

    private static BigDecimal satoshi = new BigDecimal("0.00000001");
    /**
     * 计算 算力
     * @param name
     * @param value
     * @return
     */
    public static long getHashRate(String name,long value){

        return 0;
    }

    /**
     *  reject_15m+stale_15m / (reject_15m + accept_15m + stale_15m)
     *  计算拒绝率
     */
    public static double getRejectRate(Long accept15m, Long stale15m, Long reject15m) {
        if(accept15m+reject15m+stale15m == 0){
            return 0d;
        }
        return (double)(reject15m+stale15m)/(accept15m+reject15m+stale15m);
    }

    // earn * (1 + net_fee) * (1 - pool_fee)
    public static BigDecimal getEarnAmount(BigDecimal earn, BigDecimal netFee, BigDecimal poolFee) {
        return earn.multiply(BigDecimal.ONE.add(netFee)).multiply(BigDecimal.ONE.subtract(poolFee));
    }

    public static Double getRejectRate(BigDecimal shareAccept, BigDecimal shareStale, BigDecimal shareReject) {
        if(shareAccept.add(shareStale.add(shareReject)).compareTo(BigDecimal.ZERO) == 0){
            return 0d;
        }
        return (shareReject.add(shareStale)).divide(
                shareAccept.add(shareStale).add(shareAccept),12, RoundingMode.DOWN
        ).doubleValue();
    }
    public static BigDecimal toBtcAmount(BigDecimal amount) {
        return amount.multiply(satoshi);
    }
}
