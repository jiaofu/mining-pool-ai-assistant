package com.binance.pool.service.pool;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;

import java.util.List;

public interface PoolAlgoCoinService {

    /**
     * 获取算法和交易对的关系
     *
     * @return
     */
    List<PoolAlgoCoinBean> getPoolAlgoCoinBeans();

    /**
     * 获取币种
     *
     * @return
     */

    List<PoolCoinBean> getPoolCoinBeans();

    /**
     * 获取算法
     *
     * @return
     */
    List<PoolAlgoBean> getPoolAlgoBeans();


    void refreshMemory();
}
