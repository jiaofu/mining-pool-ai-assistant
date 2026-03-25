package com.binance.pool.service.pool;

import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.invitation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface InvitationService {


    ResultBean insertPoolUserRemoveInvitation(RemoveInvitationArg arg);


    /**
     * 下载返佣记录
     * @param uid
     * @param arg
     */
    List<InvitationReturnCommissionRet> returnCommissionDownloadJson(long uid, InvitationRecordArg arg);



    /**
     * 下载返现记录
     * @param uid
     * @param arg
     */
    List<InvitationReturnCashRet> returnCashJson(long uid, InvitationRecordArg arg);




    /**
     * 下载邀请记录
     * @param uid
     * @param arg
     * @param
     */
    List<InvitationRecordRet> recordJson(long uid, InvitationRecordArg arg);

}
