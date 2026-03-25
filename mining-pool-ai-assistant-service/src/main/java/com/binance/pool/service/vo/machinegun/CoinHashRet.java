package com.binance.pool.service.vo.machinegun;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class CoinHashRet {
    @Schema(description = "币种")
    private String coin;
    private String hash;
}
