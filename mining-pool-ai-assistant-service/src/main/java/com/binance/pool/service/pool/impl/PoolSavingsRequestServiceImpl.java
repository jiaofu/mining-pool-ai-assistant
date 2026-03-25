package com.binance.pool.service.pool.impl;

import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.bean.PoolSavingsRequest;
import com.binance.pool.dao.pool.PoolSavingsRequestDao;
import com.binance.pool.service.pool.PoolSavingsRequestService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class PoolSavingsRequestServiceImpl implements PoolSavingsRequestService {


    @Resource
    PoolSavingsRequestDao poolSavingsRequestDao;

    public LoadingCache<String, PoolSavingsRequest> poolSavingsRequestCache = CacheBuilder.newBuilder()
            .maximumSize(1000000)//设置容量上限
            .initialCapacity(100)
            .refreshAfterWrite(10, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .expireAfterAccess(10,TimeUnit.MINUTES)
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<String, PoolSavingsRequest>() {
                @Override
                public PoolSavingsRequest load(String key) throws Exception {

                    String [] strKey =  key.split("-");

                    Long uidPoolBinance= Long.parseLong(strKey[0]);
                    Long poolSavingsId = Long.parseLong(strKey[1]);
                     Integer type = Integer.parseInt(strKey[2]);
                    PoolSavingsRequest request = poolSavingsRequestDao.getPoolSavingsRequest(uidPoolBinance,poolSavingsId,type);
                    if(request == null){
                        request = new PoolSavingsRequest();
                    }
                    return request;
                }
            });

    @Override
    public PoolSavingsRequest getPoolSavingsRequestByPoolUid(Long uidPoolBinance, Long poolSavingsId, Integer type) {
       String key = new StringBuilder().append(uidPoolBinance).append("-").append(poolSavingsId).append("-").append(type).toString();
        PoolSavingsRequest request = poolSavingsRequestCache.getUnchecked(key);
        if(request == null || request.getId() == null){
            return  null;
        }
        return request;
    }
}
