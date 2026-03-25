package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolReportMiningPoolBean;

import java.util.List;

public interface PoolReportMiningPoolDao {

    long insertPoolReportMiningPoolBean(PoolReportMiningPoolBean bean);


    PoolReportMiningPoolBean getPoolReportMiningPoolBean(long day, String coinName);

    PoolReportMiningPoolBean getLastBean(String coinName);

    List<PoolReportMiningPoolBean> getPoolReportMiningPoolBeans(long day);


    List<PoolReportMiningPoolBean> getGreaterThanOrEqualTo(Long day);
}
