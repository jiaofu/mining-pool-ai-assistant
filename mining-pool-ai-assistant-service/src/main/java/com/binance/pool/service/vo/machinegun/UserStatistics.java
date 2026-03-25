package com.binance.pool.service.vo.machinegun;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserStatistics {
    @Schema(description = "算力")
    private BigDecimal hashRate;
    @Schema(description = "收益")
    private BigDecimal profit;
}
