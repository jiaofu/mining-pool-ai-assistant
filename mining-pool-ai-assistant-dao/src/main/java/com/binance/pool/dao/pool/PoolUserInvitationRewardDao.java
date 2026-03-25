package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolUserInvitationRewardBean;

import java.util.List;

public interface PoolUserInvitationRewardDao {

    List<PoolUserInvitationRewardBean> getPoolUserInvitationRewardBeans(long uidBinance, Long algoId, Integer type, Long startDay, Long endDay);


    /**
     * 返现的列表
     * @param uidBinanceInvitation
     * @param algoId
     * @param type
     * @param startDay
     * @param endDay
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PoolUserInvitationRewardBean> getPoolUserInvitationRewardInvitationsBeans(long uidBinanceInvitation,Long algoId,Integer type,Long startDay,Long endDay);

}
