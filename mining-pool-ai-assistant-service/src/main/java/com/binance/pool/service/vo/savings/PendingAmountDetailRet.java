package com.binance.pool.service.vo.savings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingAmountDetailRet {
    @Schema(description = "资产name")
    private String asset;

    @Schema(description = "用户持仓资产")
    private BigDecimal pendingAmount;

    @Schema(description = "用户持仓产生的利息")
    private BigDecimal pendingAmountInterest;

    @Schema(description = "总利息金额")
    private BigDecimal totalInterestAmount;

    @Schema(description = "年化率")
    private BigDecimal annualizedRate;

    @Schema(description = "存入挖矿的时间")
    private Long poolSavingTime;
}
