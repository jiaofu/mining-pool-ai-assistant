package com.binance.pool.service.enums;

import com.alibaba.fastjson.JSONArray;
import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.util.RedisUtil;
import com.binance.pool.service.pool.PoolAlgoCoinService;
import com.binance.pool.service.util.RedisKeyUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum PoolCoinEnum {

    /**
     * 实体
     */
    INSTANCE;
    /**
     * 缓存list
     */
    private List<PoolCoinBean> listCache = new ArrayList<>();
    /**
     * 缓存Map
     */
    private Map<Long, PoolCoinBean> mapCache = new LinkedHashMap<>();

    /**
     * 缓存Map
     */
    private Map<String, PoolCoinBean> mapCacheStr = new LinkedHashMap<>();

    private PoolAlgoCoinService poolAlgoCoinService;

    public void setService(PoolAlgoCoinService poolAlgoCoinService) {
        this.poolAlgoCoinService = poolAlgoCoinService;
    }


    public List<PoolCoinBean> getlist() {
        if (CollectionUtils.isEmpty(listCache)) {
            updateOptionMemory();
        }
        return listCache;
    }

    public Map<Long, PoolCoinBean> getMap() {
        if (CollectionUtils.isEmpty(mapCache)) {
            updateOptionMemory();
        }
        return mapCache;
    }

    public Map<String, PoolCoinBean> getMapStr() {
        if (CollectionUtils.isEmpty(mapCacheStr)) {
            updateOptionMemory();
        }
        return mapCacheStr;
    }

    /**
     * <br>更新内存<br/>
     * 用于缓存更新后，后台通知前台调用更新
     *
     * @return 更新成功 返回0 否则返回-1
     */
    public synchronized void updateOptionMemory() {
        listCache.clear();
        mapCache.clear();
        mapCacheStr.clear();
        //此处考虑同步

        if (!CollectionUtils.isEmpty(listCache)) {
            return;
        }
        listCache = poolAlgoCoinService.getPoolCoinBeans();

        if (!CollectionUtils.isEmpty(listCache)) {
            for (PoolCoinBean poolCoinBean : listCache) {

                PoolCoinBean pool = mapCache.get(poolCoinBean.getId());
                if (pool == null) {
                    mapCache.put(poolCoinBean.getId(), poolCoinBean);
                }
                if (poolCoinBean.getCoinType().intValue() == 0) {
                    mapCacheStr.put(poolCoinBean.getSymbol().toUpperCase(), poolCoinBean);
                }


            }


        }
    }

    public static void main(String[] args) {
        System.out.println(1);
    }

}
