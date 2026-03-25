package com.binance.pool.service.cache.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkerStatusCacheBean {
    /**状态 MinerWorkerStatusEnum*/
    private Integer status;
    /**对应状态的矿机数量*/
    private Integer count;
}
