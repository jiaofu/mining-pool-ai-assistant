package com.binance.pool.service.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by yyh on 2020/4/24
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TimeListCache {
    //day or hour
//    private Long time;
    //workerId
    private String id;
    //算力
    private String hash;
    //拒绝率
    private Double reject;

    //earn 子账户当日earn，计算预估收益
    private BigDecimal earn;
}
