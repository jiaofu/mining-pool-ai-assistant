package com.binance.pool.service.vo.userInfo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "统计的表格")
public class StatHashrate {
    @Schema(description = "utc时间")
    private long time;
    @Schema(description = "原始算力值，需要根据单位转换")
    private String hashrate;
    @Schema(description = "拒绝率")
    private String reject;

}
