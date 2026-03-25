package com.binance.pool.service.cache.Impl;

import com.binance.pool.dao.bean.BaseBean;
import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.dao.pool.PoolCoinDao;
import com.binance.pool.service.cache.CoinCacheService;
import com.binance.pool.service.enums.PoolAlgoCoinEnum;
import com.binance.pool.service.enums.PoolAlgoEnum;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.enums.PoolCoinTypeEnum;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2020/3/13
 */
@Service
@Slf4j
public class CoinCacheServiceImpl implements CoinCacheService {

    @Autowired
    PoolCoinDao coinDao;
    @Autowired
    AlgoCoinDao algoCoinDao;
    public LoadingCache<Long, PoolCoinBean> coinCache = CacheBuilder.newBuilder()
            .maximumSize(10000)//设置容量上限
            .initialCapacity(1000)
            .expireAfterWrite(15, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, PoolCoinBean>() {
                @Override
                public PoolCoinBean load(Long coinId) throws Exception {
                    log.info("coinCache load coin cache, coinId:{}",coinId);
                    PoolCoinBean coinBean =  coinDao.findCoinById(coinId);
                    return coinBean;
                }
            });
    public LoadingCache<Integer, List<PoolCoinBean>> coinsCache = CacheBuilder.newBuilder()
            .maximumSize(10000)//设置容量上限
            .initialCapacity(1000)
            .expireAfterAccess(15, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Integer, List<PoolCoinBean>>() {
                @Override
                public List<PoolCoinBean> load(Integer coinType) throws Exception {
                    List<PoolCoinBean> coinBeans =  coinDao.findTypeCoin(coinType);
                    return coinBeans;
                }
            });

    public LoadingCache<Long, List<PoolCoinBean>> algoCoinCache = CacheBuilder.newBuilder()
            .maximumSize(10000)//设置容量上限
            .initialCapacity(1000)
            .expireAfterAccess(15, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, List<PoolCoinBean> >() {
                @Override
                public List<PoolCoinBean>  load(Long algoId) {
                    List<PoolAlgoCoinBean> algoCoinBeans = algoCoinDao.getAlgoCoinByAlgo(algoId,0);
                    List<PoolCoinBean> coinBeans = Lists.newArrayList();
                    for(PoolAlgoCoinBean algoCoinBean:algoCoinBeans){
                        try {
                            coinBeans.add(coinCache.get(algoCoinBean.getCoinId()));
                        } catch (ExecutionException e) {
                            coinBeans.add(coinDao.findCoinById(algoCoinBean.getCoinId()));
                        }
                    }
                    return coinBeans;
                }
            });

    @Override
    public PoolCoinBean getCoinById(Long coinId) {
        try{
            return coinCache.getUnchecked(coinId);
        }catch (Exception e){
            log.error("getCoinById found an error coinId:{}",coinId,e);
        }
        return coinDao.findCoinById(coinId);
    }


    @Override
    public List<PoolCoinBean> getCoinsByAlgo(long algoId) {
        try {
            return algoCoinCache.get(algoId);
        } catch (ExecutionException e) {
            List<PoolAlgoCoinBean> algoCoinBeans = algoCoinDao.getAlgoCoinByAlgo(algoId, 0);
            List<PoolCoinBean> coinBeans = Lists.newArrayList();
            for(PoolAlgoCoinBean algoCoinBean:algoCoinBeans){
                try {
                    coinBeans.add(coinCache.get(algoCoinBean.getCoinId()));
                } catch (ExecutionException e1) {
                    coinBeans.add(coinDao.findCoinById(algoCoinBean.getCoinId()));
                }
            }
            return coinBeans;
        }
    }

    @Override
    public void refreshMemory() {

        PoolAlgoEnum.INSTANCE.updateOptionMemory();
        PoolCoinEnum.INSTANCE.updateOptionMemory();
        PoolAlgoCoinEnum.INSTANCE.updateOptionMemory();
    }


}
