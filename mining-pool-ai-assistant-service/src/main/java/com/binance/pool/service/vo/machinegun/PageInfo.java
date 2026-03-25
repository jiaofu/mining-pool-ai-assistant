package com.binance.pool.service.vo.machinegun;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by yyh on 2020/4/23
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PageInfo<T> {
    private List<T> list;
    @Schema(description = "当前页码")
    private Integer page;
    @Schema(description = "pageSize")
    private Integer pageSize;
    @Schema(description = "总条数")
    private Integer totalCount;
}
