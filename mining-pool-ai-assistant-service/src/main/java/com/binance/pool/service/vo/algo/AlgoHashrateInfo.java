package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "算法列表")
public class AlgoHashrateInfo {

    @Schema(description = "挖矿算法")
    private String  algoName;          //


    @Schema(description = "挖矿算法")
    private long  algoid;


    @Schema(description = "教程url")
    private String urL;


    @Schema(description = "算法下的交易对的信息")
    private List<SymbolInfo> symbolInfos;



}
