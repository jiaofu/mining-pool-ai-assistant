package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Builder
@Table(name = "pool_user_payment")
@AllArgsConstructor
@NoArgsConstructor
public class PoolUserPaymentBean extends BaseBean {

    private Long puid;// 'ubinance的id',
    private Long uid;// 'ubinance的id',
    private Long algoId;//  '算法id',
    private Long coinId;//  '币种id',
    private Integer status;//  '0' COMMENT '0:使用 1:停止',
    private Integer paymentType;// 0:更新， 1 调用接口 ，2 完成,
    private Long day; //  修改日期
    private Long lastCoinId;// 上次支付设置
}
