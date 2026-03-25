package com.binance.pool.service.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/4/24
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkerListCache {
    /**workerId*/
    private String id;
    /**workerName*/
    private String name;
    /**lastShareTime*/
    private Long time;
    /**实时算力*/
    private String hash15m;
    /**实时拒绝率 15min拒绝率*/
    private Double reject;
    /**日均算力*/
    //可能用不到
    //数据库中不好计算这个 不设置这个，汇总数据的时候再计算
    private String hashDay;

    //最后1h曲线
    /**滑动1h算力*/
    private String hash1h;
    /**滑动1h拒绝率*/
    private Double reject1h;


}
