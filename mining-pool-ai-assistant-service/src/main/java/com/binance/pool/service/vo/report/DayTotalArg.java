package com.binance.pool.service.vo.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayTotalArg {


    @Schema(description = "日期",requiredMode = Schema.RequiredMode.REQUIRED,type = "integer", format = "int64")
    @Range(min = 20200606,max = 20301212)
    private Long day;
}
