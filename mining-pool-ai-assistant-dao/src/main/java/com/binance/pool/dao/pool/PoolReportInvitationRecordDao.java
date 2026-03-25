package com.binance.pool.dao.pool;


import com.binance.pool.base.bean.PoolReportInvitationRecordBean;

import java.util.Date;
import java.util.List;

public interface PoolReportInvitationRecordDao {



    List<PoolReportInvitationRecordBean> getPoolBinance(long uidPoolBinance);

    List<PoolReportInvitationRecordBean> getPoolBinance(long uidPoolBinance,Long algoId,String orderBy, Integer pageNum, Integer pageSize);

    long getPoolBinanceCount(long uidPoolBinance,Long algoId);

    List<PoolReportInvitationRecordBean> getPoolBinanceByTime(long uidPoolBinance, Long algoId, Date start, Date end);


    List<PoolReportInvitationRecordBean> getPoolBinanceByIds(long uidPoolBinance,Long algoId,List<Long> uidPoolBinanceInvitations);
}
