package com.binance.pool.service.cache;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;

import java.util.List;
import java.util.Map;

public interface PoolAlgoCoinCacheService {

    List<PoolAlgoCoinBean> getList();

    Map<Long, PoolAlgoCoinBean> getMap();

    List<PoolCoinBean> getCoinsByAlgo(long algo);

    PoolAlgoBean getAlgoByCoin(int coin);

    void updateOptionMemory();
}


