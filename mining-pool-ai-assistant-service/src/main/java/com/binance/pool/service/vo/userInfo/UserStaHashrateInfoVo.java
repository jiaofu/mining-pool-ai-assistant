package com.binance.pool.service.vo.userInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class UserStaHashrateInfoVo {



    /**有效矿机数量*/
    private Integer validNum;
    /**无效矿机数量*/
    private Integer invalidNum;


    /**实时算力*/
    private String hashRate;
    /**日均算力*/
    private String dayHashRate;
    @Schema(description = "算力统计")
    private List<StatHashrate> dayList;

    @Schema(description = "算力统计")
    private List<StatHashrate> hoursList;

    @Schema(description = "今日预估收益")
    private String profitToday;
    @Schema(description = "昨日收益")
    private String profitYesterday;

}
