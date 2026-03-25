package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "算法中交易对的用户的挖矿费率")
public class AlgoSymbolRate {
    @Schema(description = "用户真实的挖矿费率")
    private String rate;

    @Schema(description = "币种名字")
    private String symbol;

    @Schema(description = "算法名字")
    private String AlgoName;





}
