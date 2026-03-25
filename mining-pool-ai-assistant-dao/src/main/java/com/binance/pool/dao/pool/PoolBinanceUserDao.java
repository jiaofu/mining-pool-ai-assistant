package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolBinanceUserBean;

import java.util.List;

public interface PoolBinanceUserDao {
    /**
     * 插入币安用户表
     * @param poolBinanceUserBean
     * @return
     */
   long insertPoolBinanceUserBean(PoolBinanceUserBean poolBinanceUserBean);

    /**
     * 获取币安用户
     * @param uid
     * @return
     */
    PoolBinanceUserBean getPoolBinanceUserBeanByUid(long uid);


    /**
     * 获取币安的 矿池资产用户
     * @param uidPoolBinance
     * @return
     */
    public PoolBinanceUserBean getPoolBinanceUserBeanByuidPoolBinance(long uidPoolBinance);

    List<PoolBinanceUserBean> getPoolBinanceUserBeans(List<Long> uidPoolBinances);

}
