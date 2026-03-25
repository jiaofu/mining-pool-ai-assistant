package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Schema(description = "算力")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgoHashrateValue {
    @Schema(description = "utc时间")
    private long time;
    @Schema(description = "原始算力值，需要转换显示")
    private String hashrate;
}
