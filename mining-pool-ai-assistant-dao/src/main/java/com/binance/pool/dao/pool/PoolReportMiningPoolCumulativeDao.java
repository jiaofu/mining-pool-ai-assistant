package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;

import java.util.List;

public interface PoolReportMiningPoolCumulativeDao {

    long insertPoolReportMiningPoolCumulative(PoolReportMiningPoolCumulativeBean bean);


    PoolReportMiningPoolCumulativeBean getPoolReportMiningPoolCumulativeBean(long day);


    PoolReportMiningPoolCumulativeBean getLastBean();


    List<PoolReportMiningPoolCumulativeBean> getGreaterThanOrEqualTo(Long day);
}
