package com.binance.pool.service.vo.savings;

import com.binance.pool.service.util.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Data
public class UserSavingsDetailArg {

    @Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer", format = "int64")
    @NotNull
    @Range(min = 1,max = Constants.MAX_HUMAN_VALUE)
    private Long userId;


    @Schema(description = "资产",requiredMode = Schema.RequiredMode.AUTO)
    @Size(max = 20)
    private String asset;
}
