package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "首页的入参")
public class IndexArg {
    @Schema(description = "用户id")
    private Long puid;
    @Schema(description = "币安用户id")
    private Long bidBinance;

    @Schema(description = "算法的id")
    private Long algoId;
}
