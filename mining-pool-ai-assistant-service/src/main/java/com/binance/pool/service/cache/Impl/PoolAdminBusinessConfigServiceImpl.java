package com.binance.pool.service.cache.Impl;


import com.binance.pool.base.bean.PoolAdminBusinessConfigBean;
import com.binance.pool.dao.pool.PoolAdminBusinessConfigDao;
import com.binance.pool.service.cache.PoolAdminBusinessConfigService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PoolAdminBusinessConfigServiceImpl implements PoolAdminBusinessConfigService {

    @Resource
    PoolAdminBusinessConfigDao poolAdminBusinessConfigDao;


    public LoadingCache<String, Long> adminConfig = CacheBuilder.newBuilder()
            .maximumSize(1000)//设置容量上限
            .initialCapacity(20)
            .expireAfterAccess(10, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(10, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<String, Long>() {
                @Override
                public Long load(String key) throws Exception {

                    PoolAdminBusinessConfigBean poolAdminBusinessConfigBean = poolAdminBusinessConfigDao.getPoolSendAdminMsgByCode(key);

                    if (poolAdminBusinessConfigBean == null || StringUtils.isEmpty(poolAdminBusinessConfigBean.getValue())) {
                        return 0L;
                    }
                    return Long.parseLong(poolAdminBusinessConfigBean.getValue());

                }
            });

    @Override
    public Long getPoolAdminBusinessConfigStatus(String code) {
        return adminConfig.getUnchecked(code);
    }
}
