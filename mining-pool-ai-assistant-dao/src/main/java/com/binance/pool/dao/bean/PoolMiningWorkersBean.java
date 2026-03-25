package com.binance.pool.dao.bean;

import com.binance.pool.dao.bean.coin.MiningWorkersBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by yyh on 2020/3/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * 同mining_workers,去除了reject_detail_15m,reject_detail_1h
 * puid workerId唯一索引
 */
@Table(name = "pool_mining_workers")
public class PoolMiningWorkersBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workerId;
    private Long puid;
    /**
     * 矿池默认分组id=-1*puid
     */
    private Long groupId;
    private String workerName;
    @Column(name = "accept_1m")
    private Long accept1m;
    @Column(name = "accept_5m")
    private Long accept5m;
    @Column(name = "accept_15m")
    private Long accept15m;
    @Column(name = "stale_15m")
    private Long stale15m;
    @Column(name = "reject_15m")
    private Long reject15m;
    @Column(name = "accept_1h")
    private Long accept1h;
    @Column(name = "stale_1h")
    private Long stale1h;
    @Column(name = "reject_1h")
    private Long reject1h;
    private Long acceptCount;
    private String lastShareIp;
    private Date lastShareTime;
    private String minerAgent;
    private Date createdAt;
    private Date updatedAt;

    @Transient
    private Long factor;

    public static PoolMiningWorkersBean transferBean(MiningWorkersBean workersBean){
        if(workersBean == null){
            return null;
        }
        PoolMiningWorkersBean poolMiningWorkersBean = PoolMiningWorkersBean.builder()
                .workerId(workersBean.getWorkerId())
                .puid(workersBean.getPuid())
                .groupId(workersBean.getGroupId())
                .workerName(workersBean.getWorkerName())
                .accept1m(0l)
                .accept5m(0l)
                .accept15m(workersBean.getAccept15m())
                .stale15m(workersBean.getStale15m())
                .reject15m(workersBean.getReject15m())
                .accept1h(workersBean.getAccept1h())
                .stale1h(workersBean.getStale1h())
                .reject1h(workersBean.getReject1h())
                .acceptCount(0l)
                .lastShareIp("")
                .lastShareTime(workersBean.getLastShareTime())
//                .minerAgent(workersBean.getMinerAgent())
//                .createdAt(workersBean.getCreatedAt())
//                .updatedAt(workersBean.getUpdatedAt())
                .factor(workersBean.getFactor())
                .build();
        return poolMiningWorkersBean;
    }
}
