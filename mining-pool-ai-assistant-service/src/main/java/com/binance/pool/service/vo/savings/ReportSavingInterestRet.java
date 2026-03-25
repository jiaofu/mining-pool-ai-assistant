package com.binance.pool.service.vo.savings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSavingInterestRet {
    @Schema(description = "昨日")
    private BigDecimal yesterdayAmount;//decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '昨日',
    @Schema(description = "(t - 1) ~ (t - 30) day的收益")
    private BigDecimal dayThirtyAmount; //decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '(t - 1) ~ (t - 30) day的收益',
    @Schema(description = "本月")
    private BigDecimal monthAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本月',
    @Schema(description = "本季度")
    private BigDecimal seasonAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本季度',
    @Schema(description = "本年")
    private BigDecimal yearAmount;// decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '本年',

    @Schema(description = "所有的利息")
    private BigDecimal totalAmount;// 所有的利息
    @Schema(description = "日期")
    private Long day;// '日期',
}
