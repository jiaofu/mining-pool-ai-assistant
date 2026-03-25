package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "pool_coin_hash_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolCoinHashInfoBean  extends BaseBean{

    private BigDecimal hashrate;
    private BigDecimal difficulty;
    private Long coinId;
}
