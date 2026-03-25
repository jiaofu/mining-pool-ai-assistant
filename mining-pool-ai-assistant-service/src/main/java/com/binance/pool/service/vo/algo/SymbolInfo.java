package com.binance.pool.service.vo.algo;


import com.binance.pool.service.vo.algo.AlgoHashrateValue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "交易对算力信息")
public class SymbolInfo {
    @Schema(description = "名字")
    private String symbol;

    @Schema(description = "全网算力")
    private String allHash;

    @Schema(description = "矿池算力")
    private String poolHash;

    @Schema(description = "有效旷工")
    private long effectiveCount;

    @Schema(description = "当前难度")
    private String difficulty;

    @Schema(description = "挖矿费率(默认的挖矿费率)")
    private String rate;

    @Schema(description = "'0 使用pps计算费率，1 使用fpps计算费率")
    private int type;

    @Schema(description = "算力")
    private List<AlgoHashrateValue> hashrateList;



}
