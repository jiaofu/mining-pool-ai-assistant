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
public class InvitationReturnCommissionRet {
    @Schema(description = "日期")
    private Long date;
    @Schema(description = "邀请人矿池Id")
    private Long invitation_user_id;
    @Schema(description = "日均算力")
    private String day_hash;
    @Schema(description = "单位")
    private String unit;
    @Schema(description = "挖矿收益")
    private String miner_profit;
    @Schema(description = "奖励比例")
    private String reward_rate;
    @Schema(description = "奖励金额")
    private String reward_amount;
}
