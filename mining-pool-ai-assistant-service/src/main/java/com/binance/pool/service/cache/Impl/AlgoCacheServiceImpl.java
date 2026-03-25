package com.binance.pool.service.cache.Impl;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.dao.pool.AlgoDao;
import com.binance.pool.service.cache.AlgoCacheService;
import com.binance.pool.service.cache.PoolAdminBusinessConfigService;
import com.binance.pool.service.config.SysConfig;
import com.binance.pool.service.enums.AlgoStatusEnum;
import com.binance.pool.service.enums.PoolAlgoEnum;
import com.binance.pool.service.vo.index.AlgoInfoRet;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2020/3/14
 */

@Service
@Slf4j
public class AlgoCacheServiceImpl implements AlgoCacheService {

    @Resource
    SysConfig sysConfig;

    @Autowired
    AlgoDao algoDao;

    @Autowired
    AlgoCoinDao algoCoinDao;


    @Resource
    PoolAdminBusinessConfigService poolAdminBusinessConfigService;


    public LoadingCache<Integer, List<PoolAlgoBean> > algoCache = CacheBuilder.newBuilder()
            .maximumSize(10000)//设置容量上限
            .initialCapacity(1000)
            .expireAfterAccess(15, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Integer, List<PoolAlgoBean> >() {
                @Override
                public List<PoolAlgoBean>  load(Integer status) {
                    List<PoolAlgoBean> coinBean =  algoDao.getPoolAlgoBeans(status);
                    return coinBean;
                }
            });
    public LoadingCache<Integer, PoolAlgoBean > algoCoinCache = CacheBuilder.newBuilder()
            .maximumSize(10000)//设置容量上限
            .initialCapacity(1000)
            .expireAfterAccess(15, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Integer, PoolAlgoBean>() {
                @Override
                public PoolAlgoBean  load(Integer coinId) {
                    PoolAlgoCoinBean algoCoin =  algoCoinDao.getAlgoCoinByCoin(coinId);
                    if(algoCoin!=null){
                        return algoDao.findById(algoCoin.getAlgoId());
                    }
                    return null;
                }
            });
    @Override
    public List<PoolAlgoBean> getAllValidAlgo() {
        try {
            List<PoolAlgoBean> algoBeans = algoCache.get(AlgoStatusEnum.NORMAL.getStatus());
            if(!CollectionUtils.isEmpty(algoBeans)){
                algoBeans = algoDao.getPoolAlgoBeans(AlgoStatusEnum.NORMAL.getStatus());
            }
            return algoBeans;
        } catch (ExecutionException e) {
            return algoDao.getPoolAlgoBeans(AlgoStatusEnum.NORMAL.getStatus());
        }
    }

    @Override
    public PoolAlgoBean getAlgoByCoin(int coinId) {
        try {
            return algoCoinCache.get(coinId);
        } catch (ExecutionException e) {
            PoolAlgoCoinBean algoCoin =  algoCoinDao.getAlgoCoinByCoin(coinId);
            if(algoCoin!=null){
                return algoDao.findById(algoCoin.getAlgoId());
            }
        }
        return null;
    }


    @Override
    public List<AlgoInfoRet> getAlgoInfo() {
        List<AlgoInfoRet>  algoInfoRets = new ArrayList<>();
        try {
            algoInfoRets = getAlgoInfoRet();
            if (!CollectionUtils.isEmpty(algoInfoRets)) {
                algoInfoRets.forEach(bean -> {
                    if (bean.getAlgoId() - 1 == 0 && !StringUtils.isEmpty(bean.getAlgoName())) {
                        bean.setAlgoName(bean.getAlgoName().toUpperCase());
                    }
                });
            }
            return algoInfoRets;

        } catch (Exception ex) {
            log.error(" algoInfoRet 异常信息", ex);
        }
        return algoInfoRets;

    }

    private List<AlgoInfoRet> getAlgoInfoRet() {
        List<PoolAlgoBean> algoBeans = PoolAlgoEnum.INSTANCE.getlist();
        if (algoBeans == null) {
            return Collections.EMPTY_LIST;
        }
        Long hideAlgo = poolAdminBusinessConfigService.getPoolAdminBusinessConfigStatus("hide_algo");
        List<AlgoInfoRet> list = new ArrayList<>();
        for (PoolAlgoBean poolAlgoBean : algoBeans) {
            AlgoInfoRet algoInfoRet = new AlgoInfoRet();
            algoInfoRet.setAlgoId(poolAlgoBean.getId());
            algoInfoRet.setAlgoName(poolAlgoBean.getName());
            algoInfoRet.setPoolIndex(poolAlgoBean.getPoolIndex());
            algoInfoRet.setUnit(poolAlgoBean.getUnit());
            algoInfoRet.setStatus(0);
            if(poolAlgoBean.getId()==hideAlgo){
                algoInfoRet.setStatus(1);
            }
            list.add(algoInfoRet);
        }

        if (sysConfig.getCancelAlgoId() != null && sysConfig.getCancelAlgoId().size() > 0) {
            list = list.stream().filter(q -> !sysConfig.getCancelAlgoId().contains(q.getAlgoId())).collect(Collectors.toList());
        }
        return list;
    }

}
