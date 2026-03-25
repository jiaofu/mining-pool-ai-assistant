package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolCoinBean;

import java.util.List;

/**
 * @author yyh
 * @date 2020/3/13
 */
public interface PoolCoinDao {
    PoolCoinBean findCoinById(Long coinId);

    List<PoolCoinBean> findStatusCoin(Integer status);

    List<PoolCoinBean> findTypeCoin(Integer coinType);
}
