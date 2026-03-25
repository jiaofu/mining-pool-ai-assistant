package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;

import java.util.List;

public interface PoolReportSavingsInterestDao {
    List<PoolReportSavingsInterestBean> getPoolReportSavingsInterests();


    PoolReportSavingsInterestBean getPoolReportSavingsInterestBean(Long uidPoolBinance);


}
