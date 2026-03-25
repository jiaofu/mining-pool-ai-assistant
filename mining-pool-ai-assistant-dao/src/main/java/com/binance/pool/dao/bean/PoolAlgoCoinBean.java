package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_algo_coin")
public class PoolAlgoCoinBean extends BaseBean {


    private Long coinId; // '币种id',
    private Integer status; //'0:使用 1:停止',
    private Long algoId; // '算法id',


}
