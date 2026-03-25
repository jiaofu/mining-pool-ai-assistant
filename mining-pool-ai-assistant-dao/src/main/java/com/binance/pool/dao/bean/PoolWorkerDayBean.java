package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yyh on 2020/4/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_worker_day")
public class PoolWorkerDayBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 为0代表矿池
     */
    private Long puid;
    /**
     * 为0代表所有的矿工
     */
    private String workerId;
    /**
     * 挖的币种
     */
    private Long coinId;
    private String region;
    private BigDecimal speed;

    //曲线显示用到的数据
    private Integer validNum;
    private Double reject;//拒绝率
    private Integer dataType;//0正常数据，1需要加载到缓存的数据，2已经加载到缓存的数据
    private Long day;
    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',


    @Transient
    //earn 子账户当日earn，计算预估收益
    private BigDecimal earn;
}
