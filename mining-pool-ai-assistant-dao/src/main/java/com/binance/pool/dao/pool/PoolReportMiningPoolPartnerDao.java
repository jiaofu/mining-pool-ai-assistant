package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;

import java.util.List;

public interface PoolReportMiningPoolPartnerDao {

    long insertPoolReportMiningPoolPartner(PoolReportMiningPoolPartnerBean bean);


    PoolReportMiningPoolPartnerBean getPoolReportMiningPoolPartnerBean(long day, String coinName);

    PoolReportMiningPoolPartnerBean getLastBean(String coinName);

    List<PoolReportMiningPoolPartnerBean> getPoolReportMiningPoolPartnerBeans(long day);

    List<PoolReportMiningPoolPartnerBean> getGreaterThanOrEqualTo(Long day);
}
