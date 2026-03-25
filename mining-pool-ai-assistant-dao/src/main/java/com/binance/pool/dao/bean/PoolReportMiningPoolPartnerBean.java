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
@Table(name = "pool_report_mining_pool_partner")
@AllArgsConstructor
@NoArgsConstructor
public class PoolReportMiningPoolPartnerBean extends BaseBean {
    @Schema(description = "账单日期")
    private Long day; //'账单日期',
    @Schema(description = "币种名称缩写:btc")
    private String coinName; //'币种名称缩写:btc',
    @Schema(description = "爆块数量")
    private Integer output; // '爆块数量',
    @Schema(description = "合作方算力")
    private BigDecimal partnerHashrate; // '合作方算力',
    @Schema(description = "爆块收益")
    private BigDecimal outputIncome; // '爆块收益',
    @Schema(description = "分摊盈亏")
    private BigDecimal apportionProfit; // '分摊盈亏',
    @Schema(description = "理论支出")
    private BigDecimal theoryExpenditure; // '理论支出',

    @Schema(description = "合作方中币安扣点收入")
    private BigDecimal partnerRateIncome; // '合作方中币安扣点收入',

    @Schema(description = "手续费收入")
    private BigDecimal partnerFeeIncome; //'手续费收入',
    @Schema(description = "盈亏")
    private BigDecimal profit; // '盈亏',
    @Schema(description = "lucky")
    private BigDecimal lucky; // 'lucky',



}
