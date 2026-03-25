package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;

import java.util.List;

public interface PoolReportMiningPoolGunDao {

    long insertPoolReportMiningPoolGunBean(PoolReportMiningPoolGunBean bean);


    PoolReportMiningPoolGunBean getPoolReportMiningPoolGunBean(long day);

    PoolReportMiningPoolGunBean getLastBean();

    List<PoolReportMiningPoolGunBean> getGreaterThanOrEqualTo(Long day);
}
