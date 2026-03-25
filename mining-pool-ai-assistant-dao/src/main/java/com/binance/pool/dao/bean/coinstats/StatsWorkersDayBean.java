package com.binance.pool.dao.bean.coinstats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats_workers_day")
public class StatsWorkersDayBean {

    private Long puid;
    private String workerId;
    private Long day;
    private Long shareAccept;
    private Long shareStale;
    private Long shareReject;
    private Double rejectRate;

    @Transient
    private BigDecimal earn;

    /**
     * 相同的(puid,worker)累加算力，updatedAt选最新的
     * 仅计算算力适用，score和earn不能用
     */
    public StatsWorkersDayBean add(StatsWorkersDayBean other){
        if(other == null){
            return this;
        }
        if(this.puid-other.getPuid()!=0
                || !StringUtils.equals(this.workerId,other.getWorkerId())
                || this.day-other.getDay()!=0){
            throw new RuntimeException("StatsWorkersDayBean puid,workerId,hour not equal can not add ");
        }
        StatsWorkersDayBean result = StatsWorkersDayBean.builder()
                .puid(this.puid)
                .workerId(this.workerId)
                .day(this.day)
                .shareAccept(this.shareAccept+other.getShareAccept())
                .shareStale(this.shareStale+other.getShareStale())
                .shareReject(this.shareReject+other.getShareReject())
//                .earn(this.getEarn().add(other.getEarn()))
                .build();
        result.setRejectRate((double) ((result.shareReject+result.shareStale)/(result.shareReject+result.shareStale+result.shareAccept)));
        return result;
    }
}
