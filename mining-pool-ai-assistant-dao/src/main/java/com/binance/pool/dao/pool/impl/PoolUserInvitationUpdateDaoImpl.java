package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolUserInvitationUpdateBean;

import com.binance.pool.base.mapper.PoolUserRemoveInvitationMapper;
import com.binance.pool.dao.pool.PoolUserInvitationUpdateDao;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;


@Repository
public class PoolUserInvitationUpdateDaoImpl implements PoolUserInvitationUpdateDao {

    @Resource
    PoolUserRemoveInvitationMapper mapper;
    @Override
    public Integer saveRemoveInvitation(PoolUserInvitationUpdateBean bean) {
        return mapper.insert(bean);
    }
}
