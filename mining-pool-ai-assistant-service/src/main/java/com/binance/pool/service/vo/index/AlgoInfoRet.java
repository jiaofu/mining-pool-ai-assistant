package com.binance.pool.service.vo.index;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "算法信息")

public class AlgoInfoRet {
    @Schema(description = "挖矿算法")
    private String  algoName;          //


    @Schema(description = "挖矿算法id")
    private long  algoId;


    @Schema(description = "排序")
    private Integer poolIndex;//排序,0

    @Schema(description = "单位")
    private String unit;//  单位

    @Schema(description = "是否显示，0:正常显示 1：不显示")
    private Integer status;
}
