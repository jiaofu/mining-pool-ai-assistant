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
 * Created by yyh on 2020/8/21
 * 资产申赎记录
 */
@Data
@Builder
@Table(name = "pool_savings_request")
@AllArgsConstructor
@NoArgsConstructor
public class PoolSavingsRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long puid;
    private Long uidPoolBinance;
    private Long poolSavingsId;
    /**子账户名*/
    private String poolUsername;
    private Long day;
    /**0:系统生成的存入记录 1:部分赎回 2:全部赎回*/
    private Integer type;
    private Long coinId;
    /**申购，赎回数量*/
    private BigDecimal amount;
    /**0 存入  1: 赎回中  2：发起赎回 3：已赎回*/
    private Integer status;

    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',
}
