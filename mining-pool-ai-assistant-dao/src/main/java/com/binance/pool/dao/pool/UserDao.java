package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolUserBean;

import java.util.List;

public interface UserDao {


    List<PoolUserBean> getAllPoolUsers(int pageNum, int pageSize,int status);

    /**
     * 根据币安id获取所有的用户
     * @param uidBinance
     * @return
     */
    List<PoolUserBean> getUsersByBinanceId(long uidBinance);


    /**
     * 获取所有的币安用户
     * @return
     */
    List<PoolBinanceUserBean> getPoolBinanceUserBeans();

    /**
     * 获取账户
     * @param id
     * @return
     */
    PoolUserBean getPoolUserBeanById(long id);

    Integer updateDataSource(Long id, String databases);
}
