package com.binance.pool.service.vo.machinegun;

import com.binance.pool.dao.bean.PoolGunEarnRecord;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by yyh on 2020/5/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "原始收益")
public class OriginalIncomeRet {
    @Schema(description = "子账号名称")
    private Long puid;
    @Schema(description = "挖矿币种")
    private String coinName;
    @Schema(description = "日均算力")
    private BigDecimal speed;
    @Schema(description = "手续费")
    private BigDecimal serviceFee;
    @Schema(description = "结算类型 fpps")
    private String settleType;
    @Schema(description = "支付币种")
    private String paymentCoinName;
    @Schema(description = "数量 加上矿工费，扣掉手续费")
    private BigDecimal amount;
//    private Long day;

}
