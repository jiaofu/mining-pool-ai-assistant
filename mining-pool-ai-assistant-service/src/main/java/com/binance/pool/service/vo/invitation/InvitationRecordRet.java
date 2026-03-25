package com.binance.pool.service.vo.invitation;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.sts.endpoints.internal.Value;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRecordRet {

    @Schema(description = "邀请人矿池Id")
    private Long invitation_user_id;

    @Schema(description = "邀请人矿池Id")
    private String hour_hash_rate;


    @Schema(description = "单位")
    private String unit;



    @Schema(description = "算力波动")
    private String hash_wave_rate;

    @Schema(description = "奖励比例")
    private String reward_rate;



    @Schema(description = "累计奖励")
    private String total_amount;
}
