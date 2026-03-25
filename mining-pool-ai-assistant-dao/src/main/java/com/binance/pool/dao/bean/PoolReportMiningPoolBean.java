package com.binance.pool.dao.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Data
@Builder
@Table(name = "pool_report_mining_pool")
@AllArgsConstructor
@NoArgsConstructor
public class PoolReportMiningPoolBean  extends BaseBean {

    @Schema(description = "账单日期")
    private Long day;// '账单日期',
    @Schema(description = "新增用户数")
    private Integer newUsers;//  '新增用户数',
    @Schema(description = "接入算力用户数")
    private Integer userNumber;//  '接入算力用户数'
    @Schema(description = "币种名称缩写:btc")
    private String coinName;//  '币种名称缩写:btc',
    @Schema(description = "矿池算力")
    private BigDecimal poolHashrate;//  '矿池算力',
    @Schema(description = "全网算力")
    private BigDecimal allHashrate;//  '全网算力'
    @Schema(description = "爆块数")
    private Integer output;//  '爆块数',
    @Schema(description = "收入")
    private BigDecimal outputIncome;//  '收入',
    @Schema(description = "总支出")
    private BigDecimal expenditure;//  '总支出',
    @Schema(description = "盈亏")
    private BigDecimal profit;//  '盈亏',
    @Schema(description = "lucky")
    private BigDecimal lucky;//  'lucky',




}
