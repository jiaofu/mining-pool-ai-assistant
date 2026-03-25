package com.binance.pool.service.vo.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolUserInvitationRecordRet {

    @Schema(description = "被邀请人UId")
    private Long uidInvitation;   // '被邀请人UId',

    @Schema(description = "被邀请人矿池Id")
    private Long uidPoolBinanceInvitation;   // '被邀请人矿池Id',
    @Schema(description = " 返佣比例")
    private BigDecimal returnCommissionRate;// '返佣比例',

    @Schema(description = " 最近1小时算力")
    private BigDecimal hashRate;//最近1小时算力

    @Schema(description = " 算力波动")
    private Double hashWave;//算力波动

    @Schema(description = " 收益 key是coinName value为收益")
    private Map<String,String> reward;

    @Schema(description = " 所有的收益")
    private BigDecimal decimal;

    @Schema(description = "邀请时间")
    private Long invitationTime;
}
