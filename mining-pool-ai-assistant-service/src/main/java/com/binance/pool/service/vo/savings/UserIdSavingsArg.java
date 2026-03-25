package com.binance.pool.service.vo.savings;

import com.binance.pool.service.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "用户的id")
public class UserIdSavingsArg {
    @Schema(description = "用户id",type = "integer", format = "int64",requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_VALUE)
    private Long userId;


}
