package com.binance.pool.service.cache;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.PoolUserPaymentBean;
import com.binance.pool.service.vo.machinegun.UserStatistics;

import java.util.List;
import java.util.Map;

public interface UserCacheServer {

    /**
     * 获取账号信息
     *
     * @param uidBinance
     * @return
     */

    PoolBinanceUserBean getPoolBinanceUserBean(long uidBinance);


    PoolRequestApiConfigBean getPoolRequestApiConfigBean(String key);






}
