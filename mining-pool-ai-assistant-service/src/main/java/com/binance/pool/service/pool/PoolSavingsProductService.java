package com.binance.pool.service.pool;

import com.binance.pool.dao.bean.PoolSavingsProduct;

public interface PoolSavingsProductService {

    PoolSavingsProduct getPoolSavingsProductByIdCache (Long id);
}
