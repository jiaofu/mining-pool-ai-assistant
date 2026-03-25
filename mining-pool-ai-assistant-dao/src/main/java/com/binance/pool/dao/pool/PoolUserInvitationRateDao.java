package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolUserInvitationRateBean;

import java.util.List;

public interface PoolUserInvitationRateDao {

    PoolUserInvitationRateBean getPoolUserInvitationRate(long algoId);

    PoolUserInvitationRateBean getPoolUserInvitationRateAlgo(long algoId);
    long insertPoolUserInvitationRate(PoolUserInvitationRateBean bean);

    /**
     * 获取 邀请费率
     * @param algoId
     * @return
     */
    PoolUserInvitationRateBean getPoolUserInvitationRate(long uid,long uidBinanceInvitation, long algoId);


    List<PoolUserInvitationRateBean> getPoolUserInvitationRates(long uid, List<Long> uidBinanceInvitations, long algoId);
}
