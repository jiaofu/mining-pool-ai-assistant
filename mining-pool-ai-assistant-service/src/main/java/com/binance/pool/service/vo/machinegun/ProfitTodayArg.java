package com.binance.pool.service.vo.machinegun;

import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.validator.HarmlessString;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "获取利润")
public class ProfitTodayArg {
    @Range(min =0 ,max = Constants.MAX_HUMAN_VALUE)
    @Schema(description = "用户列表参数")
    Long puid;

    @Size(max = 20)
    @HarmlessString
    @Schema(description = "币种列表参数")
    String coin;


    @Schema(description = "页码，为空默认第一页，从1开始")
    @Range(min = 1)
    private Integer pageIndex;
    @Schema(description = "pageSize 最大3000，超过3000按3000计算")
    @Range(min = 1)
    private Integer pageSize;
}
