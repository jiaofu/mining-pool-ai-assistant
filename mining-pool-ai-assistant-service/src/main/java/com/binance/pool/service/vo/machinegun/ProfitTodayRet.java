package com.binance.pool.service.vo.machinegun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfitTodayRet {

    @Schema(description = "矿池账户的id")
    private String puid;
    @Schema(description = "15min实时算力")
//    @Schema(description = "日均算力")
    private String dayHashRate;

    @Schema(description = "预估收益列表")
    private List<EstimatedProfitVo> profit;
}
