package com.binance.pool.service.cache.bean;

import com.binance.pool.dao.bean.coinstats.StatsWorkersDayBean;
import com.binance.pool.dao.bean.coinstats.StatsWorkersHourBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerKlineValue {
    //day or hour
    //time
    private Long t;
    //getShareAccept
    private Long sa;
    //getShareStale
    private Long ss;
    //getShareReject
    private Long sr;

    public static WorkerKlineValue toDayKlineValue(StatsWorkersDayBean dayBean) {
        return WorkerKlineValue.builder()
                .t(dayBean.getDay())
                .sa(dayBean.getShareAccept())
                .ss(dayBean.getShareStale())
                .sr(dayBean.getShareReject())
                .build();
    }

    public static WorkerKlineValue toHourKlineValue(StatsWorkersHourBean hourBean) {
        return WorkerKlineValue.builder()
                .t(hourBean.getHour())
                .sa(hourBean.getShareAccept())
                .ss(hourBean.getShareStale())
                .sr(hourBean.getShareReject())
                .build();
    }
}
