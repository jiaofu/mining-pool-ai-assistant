package com.binance.pool.service.cache.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkerGroupCacheBean {
    /**groupId -2 全部 -1默认，其他*/
    private Long groupId;
    private String groupName;
    /**所有矿机数量*/
    private int totalNum;
    /**有效矿机数量*/
    private int validNum;
    /**失效矿机数量*/
    private int offlineNum;
    /**无效矿机数量 代码中是失效的状态*/
    private int invalidNum;
    /**实时算力*/
    private BigDecimal hashRate;
    /**日均算力*/
    private BigDecimal dayHashRate;
}
