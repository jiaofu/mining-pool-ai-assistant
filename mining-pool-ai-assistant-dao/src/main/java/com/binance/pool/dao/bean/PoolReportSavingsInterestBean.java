package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@Table(name = "pool_report_savings_interest")
@AllArgsConstructor
@NoArgsConstructor
public class PoolReportSavingsInterestBean extends BaseBean {

    private Long uidPoolBinance;//'本人矿池Id',
    private BigDecimal yesterdayAmount;//decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '昨日',
    private BigDecimal dayThirtyAmount; //decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '(t - 1) ~ (t - 30) day的收益',
    private BigDecimal monthAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本月',
    private BigDecimal seasonAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本季度',
    private BigDecimal yearAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本年',

    private BigDecimal totalAmount;// 所有的利息


    private Long day;// '日期',
}
