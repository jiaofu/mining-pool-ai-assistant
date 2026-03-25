package com.binance.pool.service.vo.machinegun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstimatedProfitVo {
    @Schema(description = "币种")
    private String coin;
    private String num;
}
