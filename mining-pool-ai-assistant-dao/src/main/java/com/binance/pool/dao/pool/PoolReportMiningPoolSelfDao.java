package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;

import java.util.List;

public interface PoolReportMiningPoolSelfDao {

    long insertPoolReportMiningPoolSelf(PoolReportMiningPoolSelfBean bean);


    PoolReportMiningPoolSelfBean getPoolReportMiningPoolSelfBean(long day, String coinName);

    List<PoolReportMiningPoolSelfBean> getPoolReportMiningPoolSelfBean(long day);

    PoolReportMiningPoolSelfBean getLastBean(String coinName);

    List<PoolReportMiningPoolSelfBean> getGreaterThanOrEqualTo(Long day);
}
