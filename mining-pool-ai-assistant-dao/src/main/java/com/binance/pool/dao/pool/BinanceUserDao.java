package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolBinanceUserBean;

/**
 * Created by yyh on 2020/3/17
 */
public interface BinanceUserDao {
    PoolBinanceUserBean getUidBinance(Long uidBinance);
}
