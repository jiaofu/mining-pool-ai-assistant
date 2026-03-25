package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolUserInvitationRateBean;
import com.binance.pool.base.mapper.PoolUserInvitationRateMapper;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.pool.PoolUserInvitationRateDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Repository
public class PoolUserInvitationRateDaoImpl implements PoolUserInvitationRateDao {
    @Resource
    PoolUserInvitationRateMapper mapper;

    @Override
    public PoolUserInvitationRateBean getPoolUserInvitationRate(long algoId) {
        Example example = new Example(PoolUserInvitationRateBean.class);
        example.createCriteria()
                .andEqualTo("status", 0).andEqualTo("algoId",algoId);
        String order = String.format("id desc limit 1 ");
        example.setOrderByClause(order);
        return mapper.selectOneByExample(example);
    }

    @Override
    public PoolUserInvitationRateBean getPoolUserInvitationRateAlgo(long algoId) {
        Example example = new Example(PoolUserInvitationRateBean.class);
        example.createCriteria()
                .andEqualTo("status", 0).
                andEqualTo("algoId",algoId)
                .andEqualTo("uidBinance",0)
                .andEqualTo("uidBinanceInvitation",0);
        String order = String.format("id desc limit 1 ");
        example.setOrderByClause(order);
        return mapper.selectOneByExample(example);
    }

    @Override
    public long insertPoolUserInvitationRate(PoolUserInvitationRateBean bean) {
        return mapper.insertSelective(bean);
    }

    @DBAnno("dbRead")
    @Override
    public PoolUserInvitationRateBean getPoolUserInvitationRate(long uid, long uidBinanceInvitation, long algoId) {
        Example example = new Example(PoolUserInvitationRateBean.class);
        example.createCriteria()
                .andEqualTo("status", 0).
                andEqualTo("algoId",algoId)
                .andEqualTo("uidBinance",uid)
                .andEqualTo("uidBinanceInvitation",uidBinanceInvitation);
        String order = String.format("id desc limit 1 ");
        example.setOrderByClause(order);
        return mapper.selectOneByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolUserInvitationRateBean> getPoolUserInvitationRates(long uid, List<Long> uidBinanceInvitations, long algoId) {
        Example example = new Example(PoolUserInvitationRateBean.class);
        example.createCriteria()
                .andEqualTo("status", 0).
                andEqualTo("algoId",algoId)
                .andEqualTo("uidBinance",uid)
                .andIn("uidBinanceInvitation",uidBinanceInvitations);

        return mapper.selectByExample(example);
    }
}
