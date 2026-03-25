package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;

import java.util.List;

public interface AlgoCoinDao {


    List<PoolAlgoCoinBean> getPoolAlgoCoinBeans(int status);


    List<PoolAlgoBean> getPoolAlgoBeans(int status);


    List<PoolCoinBean> getPoolCoinBeans(int status);


    List<PoolAlgoCoinBean> getAlgoCoinByAlgo(long algoId,int status);

    PoolAlgoCoinBean getAlgoCoinByCoin(int coinId);
}
