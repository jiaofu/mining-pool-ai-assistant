package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by yyh on 2020/5/13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_user_hash_monitor")
public class PoolUserHashMonitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long puid;
    private String poolUsername;
    private Long uidPoolBinance;// uid 中矿池钱包的用户
    private Long algoId;
    private String regions;
    private BigDecimal hashRate;//最近1小时算力
    private BigDecimal dayHashRate;//日均算力
    private Double hashWave;//算力波动

//    private Date createdDate;// '创建时间',
//    private Date modifyDate; //修改时间',

}
