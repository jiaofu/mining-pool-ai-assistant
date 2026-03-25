package com.binance.pool.service.vo.invitation;

import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.validator.PastOrPresentLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;


@Data
@Schema(description = "邀请参数")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRecordArg {


    @Schema(description = "uid",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer",format = "int64")
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_VALUE)
    private Long uidBinance;   // '本人uid',

    @Schema(description = "算法id")
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_ALGO_COIN)
    private Long algoId;

    @Schema(description = "搜索日期 毫秒时间戳")
    @PastOrPresentLong
    private Long startDate;
    @Schema(description = "搜索日期 毫秒时间戳")
    @PastOrPresentLong
    private Long endDate;
}
