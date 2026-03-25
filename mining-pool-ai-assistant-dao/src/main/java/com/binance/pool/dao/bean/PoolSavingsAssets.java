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
 * 用户资产表
 */
@Data
@Builder
@Table(name = "pool_savings_assets")
@AllArgsConstructor
@NoArgsConstructor
public class PoolSavingsAssets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //可以为0，为0表示这个主账户汇总记录
    private Long puid;
    private Long uidPoolBinance;
    private Long poolSavingsId;
    /**最后计息日期*/
    private Long day;
    private Long coinId;
    /**用户持仓资产*/
    private BigDecimal pendingAmount;
    /**用户持仓产生的利息，*/
    private BigDecimal pendingAmountInterest;
    /**总利息金额*/
    private BigDecimal totalInterestAmount;
    /**历史总申购金额*/
    private BigDecimal totalApplyAmount;
    /**历史总赎回金额*/
    private BigDecimal totalRedemptionAmount;

    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',
}
