package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "首页的信息")
public class IndexRet {


    @Schema(description = "算法信息")
    private List<AlgoHashrateInfo> algoList;







}
