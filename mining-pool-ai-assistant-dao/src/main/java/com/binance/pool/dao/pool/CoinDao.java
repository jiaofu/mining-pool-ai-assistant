package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolCoinHashInfoBean;

public interface CoinDao {
    PoolCoinHashInfoBean getCoinHashInfoBean(long coinid);

}
