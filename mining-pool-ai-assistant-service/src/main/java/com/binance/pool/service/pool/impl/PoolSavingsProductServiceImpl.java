package com.binance.pool.service.pool.impl;

import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.pool.PoolSavingsProductDao;
import com.binance.pool.service.pool.PoolSavingsProductService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PoolSavingsProductServiceImpl implements PoolSavingsProductService {

    @Resource
    private PoolSavingsProductDao productDao;



    public LoadingCache<Long,PoolSavingsProduct> poolSavingsCache = CacheBuilder.newBuilder()
            .maximumSize(100)//设置容量上限
            .initialCapacity(10)
            .refreshAfterWrite(5, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .expireAfterAccess(5,TimeUnit.MINUTES)
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, PoolSavingsProduct>() {
                @Override
                public PoolSavingsProduct load(Long key) throws Exception {

                    PoolSavingsProduct product = productDao.getPoolSavingsProductById(key);
                    if(product == null){
                        product = new PoolSavingsProduct();
                    }
                    return product;
                }
            });

    @Override
    public PoolSavingsProduct getPoolSavingsProductByIdCache(Long id) {
        return poolSavingsCache.getUnchecked(id);
    }
}
