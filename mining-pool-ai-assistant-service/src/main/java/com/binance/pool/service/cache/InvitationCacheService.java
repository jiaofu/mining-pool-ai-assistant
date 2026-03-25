package com.binance.pool.service.cache;

import com.binance.pool.service.vo.invitation.PoolUserInvitationRecordRet;

import java.util.List;

public interface InvitationCacheService {

    /**
     * 获取邀请记录下载
     * @return
     */
    List<PoolUserInvitationRecordRet> userInvitationRecordByTime(Long uidPoolBinance, Long uid, long algoId, Long start, Long end);

}
