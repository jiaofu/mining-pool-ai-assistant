package com.binance.pool.service.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yyh
 * @date 2020/3/12
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkerCacheBean{
    private Long puid;
    private String workerId;
    private String workerName;
    private Long factor;
    private Long groupId;
    private Integer status;
    private Date lastShareTime;
    /**实时算力原始数据*/
    private long accept15m;
    private long stale15m;
    private long reject15m;
    private long accept1h;
    private long stale1h;
    private long reject1h;
    /**日均算力*/
    private BigDecimal dayHashRate;
    private double dayRejectRate;
    private String dataSource;
    /**实时拒绝率*/
//    private Double rejectRate;


}
