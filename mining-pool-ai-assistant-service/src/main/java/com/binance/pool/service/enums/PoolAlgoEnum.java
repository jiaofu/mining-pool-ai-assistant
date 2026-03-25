package com.binance.pool.service.enums;

import com.alibaba.fastjson.JSONArray;
import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.dao.util.RedisUtil;
import com.binance.pool.service.pool.PoolAlgoCoinService;
import com.binance.pool.service.util.RedisKeyUtil;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum PoolAlgoEnum {
    /**
     * 实体
     */
    INSTANCE;
    /**
     * 缓存list
     */
    private List<PoolAlgoBean> listCache = new ArrayList<>();
    /**
     * 缓存Map
     */
    private Map<Long, PoolAlgoBean> mapCache = new LinkedHashMap<>();





    private PoolAlgoCoinService poolAlgoCoinService;

    public void setService(PoolAlgoCoinService poolAlgoCoinService) {
        this.poolAlgoCoinService = poolAlgoCoinService;
    }


    public List<PoolAlgoBean> getlist() {
        if (CollectionUtils.isEmpty(listCache)) {
            updateOptionMemory();
        }
        return listCache;
    }

    public Map<Long, PoolAlgoBean> getMap() {
        if (CollectionUtils.isEmpty(mapCache)) {
            updateOptionMemory();
        }
        return mapCache;
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

        //此处考虑同步
        synchronized (mapCache) {
            if (!CollectionUtils.isEmpty(listCache)) {
                return;
            }
            listCache = poolAlgoCoinService.getPoolAlgoBeans();

            if (!CollectionUtils.isEmpty(listCache)) {
                for (PoolAlgoBean poolAlgoBean : listCache) {


                    PoolAlgoBean pool = mapCache.get(poolAlgoBean.getId());
                    if (pool == null) {

                        mapCache.put(poolAlgoBean.getId(), poolAlgoBean);
                    }


                }

            }
        }
    }


}
