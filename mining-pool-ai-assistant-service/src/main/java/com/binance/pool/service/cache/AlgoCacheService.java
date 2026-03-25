package com.binance.pool.service.cache;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.service.vo.index.AlgoInfoRet;

import java.util.List;

/**
 * @author yyh
 * @date 2020/3/14
 */
public interface AlgoCacheService {
    List<PoolAlgoBean> getAllValidAlgo();

    PoolAlgoBean getAlgoByCoin(int coinId);

    List<AlgoInfoRet> getAlgoInfo();
}
