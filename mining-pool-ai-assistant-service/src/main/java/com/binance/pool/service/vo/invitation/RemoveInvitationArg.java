package com.binance.pool.service.vo.invitation;

import com.binance.pool.service.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemoveInvitationArg {

    @Schema(description = "邀请人uid",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer",format = "int64")
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_VALUE)
    private Long uidBinance;   // '本人uid',

    @Schema(description = "被邀请人uid",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer",format = "int64")
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_VALUE)
    private Long uidBinanceInvitation;   // '被邀请人UId',


    @Schema(description = "'0:解绑 1:变更 默认为0",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer",format = "int32")
    @Range(min = 0,max = 1)
    private Integer requestType;//


}
