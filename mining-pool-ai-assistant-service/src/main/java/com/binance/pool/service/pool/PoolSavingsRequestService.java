package com.binance.pool.service.pool;

import com.binance.pool.dao.bean.PoolSavingsRequest;

public interface PoolSavingsRequestService {

    PoolSavingsRequest getPoolSavingsRequestByPoolUid(Long uidPoolBinance,Long poolSavingsId,Integer type);

}
