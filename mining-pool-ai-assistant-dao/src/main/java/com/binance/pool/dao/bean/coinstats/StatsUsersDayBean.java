package com.binance.pool.dao.bean.coinstats;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats_users_day")
public class StatsUsersDayBean {

    private Long puid;
    private Long day;

    private BigDecimal shareAccept;
    private BigDecimal shareStale;
    private BigDecimal shareReject;
    private String rejectDetail;
    private Double rejectRate;
    private BigDecimal score;
    private BigDecimal earn;
    private Date createdAt;
    private Date updatedAt;

    @Transient
    private Long coinId;

    public StatsUsersDayBean add(StatsUsersDayBean other) {
        if(other == null){
            return this;
        }
        if(this.puid-other.getPuid()!=0
                || this.day-other.getDay()!=0){
            throw new RuntimeException("StatsUsersDayBean puid,day not equal can not add ");
        }
        StatsUsersDayBean result = StatsUsersDayBean.builder()
                .puid(this.puid)
                .day(this.day)
                .earn(this.getEarn().add(other.getEarn()))
                .score(this.getScore().add(other.getScore()))
                .coinId(this.coinId)
                .shareAccept(this.getShareAccept().add(other.getShareAccept()))
                .shareStale(this.getShareStale().add(other.getShareStale()))
                .shareReject(this.getShareReject().add(other.getShareReject()))
                .build();
        result.setRejectRate(calRejectRate(result.getShareAccept(),result.getShareStale(),result.getShareReject()));
        return result;
    }

    private Double calRejectRate(BigDecimal shareAccept, BigDecimal shareStale, BigDecimal shareReject) {
        return (shareReject.add(shareStale)).divide(
                shareAccept.add(shareStale).add(shareAccept),12, RoundingMode.DOWN
        ).doubleValue();
    }

}
