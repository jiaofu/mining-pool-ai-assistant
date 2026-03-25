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
@Table(name = "stats_workers_hour")
public class StatsWorkersHourBean {
    private Long puid;
    private String workerId;
    private Long hour;
    private Long shareAccept;
    private Long shareStale;
    private Long shareReject;
//    private String rejectDetail;
    private Double rejectRate;
//    private BigDecimal score;
//    private BigDecimal earn;
//    private Date createdAt;
//    private Date updatedAt;


    @Transient
    private long hashRate;
    /**
     * 相同的(puid,worker)累加算力，updatedAt选最新的
     * 仅计算算力适用，score和earn不能用
     */
    public StatsWorkersHourBean add(StatsWorkersHourBean other){
        if(other == null){
            return this;
        }
        if(this.puid-other.getPuid()!=0
                || !StringUtils.equals(this.workerId,other.getWorkerId())
                || this.hour-other.getHour()!=0){
            throw new RuntimeException("StatsWorkersHourBean puid,workerId,hour not equal can not add ");
        }
        StatsWorkersHourBean result = StatsWorkersHourBean.builder()
                .puid(this.puid)
                .workerId(this.workerId)
                .hour(this.hour)
                .shareAccept(this.shareAccept+other.getShareAccept())
                .shareStale(this.shareStale+other.getShareStale())
                .shareReject(this.shareReject+other.getShareReject())
                .build();
        result.setRejectRate((double) ((result.shareReject+result.shareStale)/(result.shareReject+result.shareStale+result.shareAccept)));
        return result;
    }
}
