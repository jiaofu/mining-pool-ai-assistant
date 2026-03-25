package com.binance.pool.service.cache.Impl;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.service.cache.PoolAlgoCoinCacheService;
import com.binance.pool.service.enums.PoolAlgoEnum;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.enums.PoolCoinTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoolAlgoCoinCacheServiceImpl implements PoolAlgoCoinCacheService {

    @Resource
    AlgoCoinDao algoCoinDao;
    /**
     * 缓存list
     */
    private List<PoolAlgoCoinBean> listCache = new ArrayList<>();
    /**
     * 缓存Map
     */
    private Map<Long, PoolAlgoCoinBean> mapCache = new LinkedHashMap<>();

    private Map<Long, List<PoolCoinBean>> mapByAlgo = new LinkedHashMap<>();

    private Map<Long, PoolAlgoBean> mapByCoin = new LinkedHashMap<>();

    @Override
    public List<PoolAlgoCoinBean> getList() {
        if (CollectionUtils.isEmpty(listCache)) {
            updateOptionMemory();
        }
        return listCache;
    }

    @Override
    public Map<Long, PoolAlgoCoinBean> getMap() {
        if (CollectionUtils.isEmpty(mapCache)) {
            updateOptionMemory();
        }
        return mapCache;
    }

    /**
     * 根据算法获取币种
     *
     * @param
     * @return
     */
    @Override
    public List<PoolCoinBean> getCoinsByAlgo(long algo) {
        if (CollectionUtils.isEmpty(mapByAlgo)) {
            updateOptionMemory();
        }
        return mapByAlgo.get(algo);

    }
    /**
     * 根据币种,获取算法
     *
     * @param
     * @return
     */
    @Override
    public PoolAlgoBean getAlgoByCoin(int coin) {

        if (CollectionUtils.isEmpty(mapByCoin)) {
            updateOptionMemory();
        }
        return mapByCoin.get(coin);
    }


    /**
     * <br>更新内存<br/>
     * 用于缓存更新后，后台通知前台调用更新
     *
     * @return 更新成功 返回0 否则返回-1
     */
    @Override
    public void updateOptionMemory() {
        listCache.clear();
        mapCache.clear();
        mapByAlgo.clear();
        mapByCoin.clear();
        //此处考虑同步
        synchronized (mapCache) {
            if (!CollectionUtils.isEmpty(listCache)) {
                return;
            }
            listCache = algoCoinDao.getPoolAlgoCoinBeans(0);

            if (!CollectionUtils.isEmpty(listCache)) {
                for (PoolAlgoCoinBean poolAlgoCoinBean : listCache) {


                    PoolAlgoCoinBean pool = mapCache.get(poolAlgoCoinBean.getId());
                    if (pool == null) {

                        mapCache.put(poolAlgoCoinBean.getId(), pool);
                    }


                    List<PoolCoinBean> poolCoinBeans = mapByAlgo.get(poolAlgoCoinBean.getAlgoId());
                    if (poolCoinBeans == null) {
                        poolCoinBeans = new ArrayList<>();
                        mapByAlgo.put(poolAlgoCoinBean.getAlgoId(), poolCoinBeans);
                    }
                    PoolCoinBean poolCoinBean = PoolCoinEnum.INSTANCE.getMap().get(poolAlgoCoinBean.getAlgoId());
                    poolCoinBeans.add(poolCoinBean);

                    Collections.sort(poolCoinBeans, new Comparator<PoolCoinBean>() {
                        @Override
                        public int compare(PoolCoinBean o1, PoolCoinBean o2) {
                            if (o1.getPoolIndex() > o2.getPoolIndex()) {
                                return 1;
                            } else if (o1.getId() == o2.getId()) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    });

                    PoolAlgoBean poolAlgoBean = mapByCoin.get(poolAlgoCoinBean.getCoinId());
                    if (poolAlgoBean == null) {
                        PoolAlgoBean poolAlgoBean1 = PoolAlgoEnum.INSTANCE.getMap().get(poolAlgoCoinBean.getCoinId());
                        mapByCoin.put(poolAlgoCoinBean.getCoinId(), poolAlgoBean1);
                    }

                }

            }
        }
    }
}
