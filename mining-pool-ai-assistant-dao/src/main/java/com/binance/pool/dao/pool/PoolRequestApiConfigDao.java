package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolRequestApiConfigBean;

import java.util.List;

public interface PoolRequestApiConfigDao {

    public PoolRequestApiConfigBean getPoolRequestApiConfigBeans(String appName);
}
