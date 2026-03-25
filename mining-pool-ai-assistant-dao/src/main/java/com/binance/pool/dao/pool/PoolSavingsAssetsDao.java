package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolSavingsAssets;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
public interface PoolSavingsAssetsDao {





    List<PoolSavingsAssets> getPoolSavingsAssets(long uidPoolBinance);
}
