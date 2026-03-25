package com.binance.pool.service.vo.userInfo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(description = "用户算法信息，只在用户登录")
 public class UserHash {
    @Schema(description = "用户名")
    private String userName;
    @Schema(description = "值")
    private BigDecimal value;
    @Schema(description = "单位")
    private String unit;
}
