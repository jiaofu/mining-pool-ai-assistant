package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yyh on 2020/8/21
 * 矿池宝配置表
 */
@Data
@Builder
@Table(name = "pool_savings_product")
@AllArgsConstructor
@NoArgsConstructor
public class PoolSavingsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer type;
    private Long coinId;

    private Long algoId;
    /**0:使用 1:停止*/
    private Integer status;


    /**
     *  每日当日赎回限额
     */
    private BigDecimal redemptionAmount;

    /**个人申购上线*/
    private BigDecimal userLimit;
    /**产品申购上线*/
    private BigDecimal configLimit;
    /**年化率*/
    private BigDecimal annualizedRate;
    /**剩余的资产*/
    private BigDecimal remainingAmount;
    /**昨日申购资产*/
    private BigDecimal yesterdayApplyAmount;
    /**昨日赎回资产*/
    private BigDecimal yesterdayRedemptionAmount;
    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',
}
