package com.binance.pool.service.vo.invitation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolUserRemoveInvitationRet {

//    @Schema(description = "创建的id")
//    private Long id;
    @Schema(description = "本人uid")
    private Long uidBinance;   // '本人uid',

    @Schema(description = "被邀请人uid")
    private Long uidBinanceInvitation;   // '被邀请人uid',

    @Schema(description = "0:解绑 1:恢复使用")
    private Integer requestType;// '0:使用 1:停止',



}
