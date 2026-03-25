package com.binance.pool.dao.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Table(name = "pool_report_mining_pool_gun")
@AllArgsConstructor
@NoArgsConstructor
public class PoolReportMiningPoolGunBean extends BaseBean {
    @Schema(description = "账单日期")
    private Long day;// '账单日期',
    @Schema(description = "机枪池算力")
    private BigDecimal gunHashrate;//'机枪池算力',
    @Schema(description = "币安算力，各币种累加算力")
    private BigDecimal selfHashrate;// '币安算力，各币种累加算力',
    @Schema(description = "大陆算力：各币种累加算力")
    private BigDecimal partnerHashrate;//'大陆算力：各币种累加算力',
    @Schema(description = "大陆爆块总收入")
    private BigDecimal partnerOutputIncome;// 大陆爆块总收入
    @Schema(description = "币安爆块总收入")
    private BigDecimal selfOutputIncome;// '币安爆块总收入',
    @Schema(description = "机枪池实际支出")
    private BigDecimal gunExpenditure;// '机枪池实际支出',
    @Schema(description = "增强比例")
    private BigDecimal enhanceRatio;// '增强比例',
    @Schema(description = "机枪池收入")
    private BigDecimal gunPoolIncome;// '机枪池收入'
    @Schema(description = "机枪池盈亏")
    private BigDecimal gunPoolProfit;// '机枪池盈亏',
    @Schema(description = "机枪聚矿盈亏")
    private BigDecimal gunPoolSmartProfit;// '机枪聚矿盈亏',
    @Schema(description = "增强收益")
    private BigDecimal enhanceEarn;// '增强收益',
    @Schema(description = "理论收益")
    private BigDecimal theoryEarn;// '理论收益',
    @Schema(description = "lucky")
    private BigDecimal lucky;// 'lucky',









}
