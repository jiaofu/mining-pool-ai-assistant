package com.binance.pool.service.vo.userInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class UserStaHashrateChar {
    @Schema(description = "算力统计")
    private List<StatHashrate> listDay;

    @Schema(description = "算力统计")
    private List<StatHashrate> listHours;
}
