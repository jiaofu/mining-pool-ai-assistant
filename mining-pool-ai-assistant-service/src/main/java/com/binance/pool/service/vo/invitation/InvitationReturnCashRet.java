package com.binance.pool.service.vo.invitation;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationReturnCashRet {

    @Schema(description = "日期")
    private Long date;

    @Schema(description = "日均算力")
    private String day_hash;
    @Schema(description = "单位")
    private String unit;
    @Schema(description = "挖矿收益")
    private String miner_profit;
    @Schema(description = "返现比例")
    private String cash_rate;
    @Schema(description = "返现金额")
    private String cash_amount;
}
