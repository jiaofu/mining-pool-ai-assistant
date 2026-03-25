package com.binance.pool.service.vo.algo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "算法的名称")
public class AlgoNam {
    @Schema(description = "名称")
    private String name;
    @Schema(description = "id")
    private long id;
}
