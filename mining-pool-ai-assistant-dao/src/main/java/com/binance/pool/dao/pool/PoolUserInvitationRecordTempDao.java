package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolUserInvitationRecordTempBean;

import java.util.List;

public interface PoolUserInvitationRecordTempDao {


    /**
     * 获取用户的信息
     * @param uidBinance
     * @param uidBinanceInvitation
     * @return
     */
    PoolUserInvitationRecordTempBean getPoolUserInvitationRecordsLast(Long uidBinance,Long uidBinanceInvitation);
}
