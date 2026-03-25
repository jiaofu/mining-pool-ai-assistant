package com.binance.pool.service.vo.machinegun;

import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.validator.HarmlessString;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Created by yyh on 2020/5/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "获取原始收益")
public class OriginalIncomeArg {
    @Range(min =0 ,max = Constants.MAX_HUMAN_VALUE)
    @Schema(description = "用户id参数")
    Long puid;


    @Size(max = 20)
    @HarmlessString
    @Schema(description = "币种参数")
    String coin;

    @NotNull
    @Schema(description = "查询日期（例如：20200525），必传")
    Long day;
    @Schema(description = "页码，为空默认第一页，从1开始")
    @Range(min = 1)
    private Integer pageIndex;
    @Schema(description = "pageSize 最大3000，超过3000按3000计算")
    @Range(min = 1)
    private Integer pageSize;
}
