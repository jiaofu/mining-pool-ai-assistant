package com.binance.pool.service.cache;

import com.binance.pool.dao.bean.PoolCoinBean;

import java.util.List;

/**
 * @author yyh
 * @date 2020/3/13
 */
public interface CoinCacheService {
    PoolCoinBean getCoinById(Long coinId);


    List<PoolCoinBean> getCoinsByAlgo(long algoId);

    void  refreshMemory();
}
