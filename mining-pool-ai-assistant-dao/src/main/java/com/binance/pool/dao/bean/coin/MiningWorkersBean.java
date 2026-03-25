package com.binance.pool.dao.bean.coin;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mining_workers")
@Slf4j
/**
 * 拒绝率：reject_15m / (reject_15m + accept_15m + stale_15m)
 * hashrate (hash/s) = accepted_share * 4294967296 (btc hash) / elapsed_time (s)
 */
public class MiningWorkersBean {
    private String workerId;
    private Long puid;
    private Long groupId;
    private String workerName;
//    @Column(name = "accept_1m")
//    private Long accept1m;
//    @Column(name = "accept_5m")
//    private Long accept5m;
    @Column(name = "accept_15m")
    private Long accept15m;
    @Column(name = "stale_15m")
    private Long stale15m;
    @Column(name = "reject_15m")
    private Long reject15m;
//    @Column(name = "reject_detail_15m")
//    private String rejectDetail15m;
    @Column(name = "accept_1h")
    private Long accept1h;
    @Column(name = "stale_1h")
    private Long stale1h;
    @Column(name = "reject_1h")
    private Long reject1h;
//    @Column(name = "reject_detail_1h")
//    private String rejectDetail1h;
//    private Long acceptCount;
//    private String lastShareIp;
    private Date lastShareTime;
//    private String minerAgent;
//    private Date createdAt;
//    private Date updatedAt;

    @Transient
    private String dataSource;
    @Transient
    private Long factor;

    /**
     * 用于合并一个workerId在不同库中的情况
     */
    public MiningWorkersBean add(MiningWorkersBean other){
        if(other == null){
            return this;
        }
        if(this.puid-other.getPuid()!=0
                || !StringUtils.equals(this.workerId,other.getWorkerId())
                || this.factor - other.getFactor() !=0){
            throw new RuntimeException("MiningWorkersBean puid "+puid+",workerId "+workerId+" otherWorkerId"+other.getWorkerId()+" ,factor not equal can not add ");
        }
        if(Lists.newArrayList(this.dataSource.split("_")).contains(other.getDataSource())){
            //新加了矿机，在第二页又出现了
            log.info("MiningWorkersBean dataSource:{} puid:{} workerId:{} ds equal;",this.dataSource,this.puid,this.workerId);
            return this;
        }
        MiningWorkersBean result = MiningWorkersBean.builder()
                .puid(this.puid)
                .workerId(this.workerId)
                .factor(this.factor)
                .workerName(this.workerName)
                .groupId(this.groupId)
                .accept15m(this.accept15m+other.getAccept15m())
                .stale15m(this.stale15m+other.getStale15m())
                .reject15m(this.reject15m+other.getReject15m())
                .accept1h(this.accept1h+other.getAccept1h())
                .stale1h(this.stale1h+other.getStale1h())
                .reject1h(this.reject1h+other.getReject1h())
                .lastShareTime(this.lastShareTime.after(other.getLastShareTime())?this.lastShareTime:other.getLastShareTime())
                .dataSource(this.dataSource+"_"+other.getDataSource())
                .build();
        return result;
    }
}
