package com.binance.pool.dao.pool;


import com.binance.pool.base.bean.PoolAdminBusinessConfigBean;

public interface PoolAdminBusinessConfigDao {

    PoolAdminBusinessConfigBean getPoolSendAdminMsgByCode(String code);
}
