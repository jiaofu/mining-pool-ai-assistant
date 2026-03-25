package com.binance.pool.service.enums;

import com.alibaba.fastjson.JSONArray;
import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.dao.util.RedisUtil;
import com.binance.pool.service.pool.PoolAlgoCoinService;
import com.binance.pool.service.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.*;

@Slf4j
public enum PoolAlgoCoinEnum {


    /**
     * 实体
     */
    INSTANCE;

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

    private PoolAlgoCoinService poolAlgoCoinService;

    public void setService(PoolAlgoCoinService poolAlgoCoinService) {
        this.poolAlgoCoinService = poolAlgoCoinService;
    }

    public List<PoolAlgoCoinBean> getlist() {
        if (CollectionUtils.isEmpty(listCache)) {
            updateOptionMemory();
        }
        return listCache;
    }

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
    public PoolAlgoBean getAllgoByCoin(long coin) {

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
            listCache = poolAlgoCoinService.getPoolAlgoCoinBeans();


            if (!CollectionUtils.isEmpty(listCache)) {
                for (PoolAlgoCoinBean poolAlgoCoinBean : listCache) {


                    PoolAlgoCoinBean pool = mapCache.get(poolAlgoCoinBean.getId());
                    if (pool == null) {

                        mapCache.put(poolAlgoCoinBean.getId(), poolAlgoCoinBean);
                    }


                    // 算法和币种的关系
                    List<PoolCoinBean> poolCoinBeans = mapByAlgo.get(poolAlgoCoinBean.getAlgoId());
                    if (poolCoinBeans == null) {
                        poolCoinBeans = new ArrayList<>();
                        mapByAlgo.put(poolAlgoCoinBean.getAlgoId(), poolCoinBeans);
                    }
                    PoolCoinBean poolCoinBean = PoolCoinEnum.INSTANCE.getMap().get(poolAlgoCoinBean.getCoinId());
                    poolCoinBeans.add(poolCoinBean);


                    //

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
                        PoolAlgoBean palgoBean = PoolAlgoEnum.INSTANCE.getMap().get(poolAlgoCoinBean.getAlgoId());
                        mapByCoin.put(poolAlgoCoinBean.getCoinId(), palgoBean);
                    }

                }

            }
        }
    }
}


