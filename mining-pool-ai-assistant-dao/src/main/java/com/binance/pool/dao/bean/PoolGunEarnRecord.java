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
import java.util.Date;

/**
 * 将今日为机枪池结算的用户放入这个表中
 * Created by yyh on 2020/5/23
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "pool_gun_earn_record")
public class PoolGunEarnRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long puid;
    private Long coinId;
    private BigDecimal speed;
    private BigDecimal earn;
    private BigDecimal minerFeeRate;
    private BigDecimal feeRate;
    private Long day;
    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',
}
