package com.binance.pool.service.cache.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by yyh on 2020/4/2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerKlineCacheBean {
    //workerId为0代表puid下所有的矿机算力KLine
    private String workerId;
    private Long startHour;
    private Long endHour;
    private Long startDay;
    private Long endDay;
    private Long factor;

    private List<WorkerKlineValue> dayDatas;
    private List<WorkerKlineValue> hourDatas;

}
