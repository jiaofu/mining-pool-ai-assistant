package com.binance.pool.dao.bean.coinstats;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
 public class  StatsBean {
    private BigDecimal shareAccept;
    private BigDecimal shareStale;
    private BigDecimal shareReject;
    private String rejectDetail;
    private Double rejectRate;
    private BigDecimal score;
    private BigDecimal earn;
    private Date createdAt;
    private Date updatedAt;
}
