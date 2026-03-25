package com.binance.pool.service.cache.Impl;

import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.pool.PoolReportSavingsInterestDao;
import com.binance.pool.dao.pool.PoolSavingsAssetsDao;
import com.binance.pool.service.cache.UserSavingCacheServer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserSavingCacheServerImpl implements UserSavingCacheServer {

    @Resource
    PoolSavingsAssetsDao poolSavingsAssetsDao;

    @Resource
    PoolReportSavingsInterestDao poolReportSavingsInterestDao;



    public LoadingCache<Long, List<PoolSavingsAssets>> poolSavingsAssets = CacheBuilder.newBuilder()
            .maximumSize(100000)//设置容量上限
            .initialCapacity(10000)
            .expireAfterAccess(10, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(10, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, List<PoolSavingsAssets>>() {
                @Override
                public List<PoolSavingsAssets> load(Long uidPoolBiance) throws Exception {

                    List<PoolSavingsAssets> list = poolSavingsAssetsDao.getPoolSavingsAssets(uidPoolBiance);
                    return list;
                }
            });


    public LoadingCache<Long, PoolReportSavingsInterestBean> poolReportSavingsInterests = CacheBuilder.newBuilder()
            .maximumSize(100000)//设置容量上限
            .initialCapacity(10000)
            .expireAfterAccess(10, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(10, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, PoolReportSavingsInterestBean>() {
                @Override
                public PoolReportSavingsInterestBean load(Long uidPoolBiance) throws Exception {

                    PoolReportSavingsInterestBean bean = poolReportSavingsInterestDao.getPoolReportSavingsInterestBean(uidPoolBiance);
                    if (bean == null) {
                        bean = PoolReportSavingsInterestBean.builder().build();
                    }
                    return bean;
                }
            });


    @Override
    public List<PoolSavingsAssets> getPoolSavingsAssets(long uidPoolBinance) {
        try {
            return poolSavingsAssets.getUnchecked(uidPoolBinance);
        } catch (Exception ex) {
            log.error(" getPoolSavingsAssets:" + uidPoolBinance, ex);
            List<PoolSavingsAssets> list = poolSavingsAssetsDao.getPoolSavingsAssets(uidPoolBinance);
            return list;
        }

    }

    @Override
    public PoolReportSavingsInterestBean getPoolReportSavingsInterests(long uidPoolBinance) {
        try {
            return poolReportSavingsInterests.getUnchecked(uidPoolBinance);
        } catch (Exception ex) {
            log.error(" getPoolSavingsAssets:" + uidPoolBinance, ex);
            PoolReportSavingsInterestBean bean = poolReportSavingsInterestDao.getPoolReportSavingsInterestBean(uidPoolBinance);
            return bean;
        }
    }

    @Override
    public void invalidSavingsAssetsCache() {
        poolSavingsAssets.invalidateAll();
    }

    @Override
    public void invalidSavingsInterestsCache() {
        poolReportSavingsInterests.invalidateAll();
    }
}
