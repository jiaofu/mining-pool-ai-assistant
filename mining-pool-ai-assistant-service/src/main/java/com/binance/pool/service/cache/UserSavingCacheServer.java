package com.binance.pool.service.cache;

import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;

import java.util.List;

public interface UserSavingCacheServer {
     List<PoolSavingsAssets> getPoolSavingsAssets(long uidPoolBinance);

     PoolReportSavingsInterestBean getPoolReportSavingsInterests(long uidPoolBinance);


     void invalidSavingsAssetsCache();

     void invalidSavingsInterestsCache();
}
