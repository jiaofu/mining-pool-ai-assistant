package com.binance.pool.dao.pool;

import com.binance.pool.base.bean.PoolUserInvitationUpdateBean;


public interface PoolUserInvitationUpdateDao {

    Integer saveRemoveInvitation(PoolUserInvitationUpdateBean bean);
}
