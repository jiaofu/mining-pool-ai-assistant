package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolUserInvitationRewardBean;
import com.binance.pool.base.mapper.PoolUserInvitationRewardMapper;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.pool.PoolUserInvitationRewardDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class PoolUserInvitationRewardDaoImpl implements PoolUserInvitationRewardDao {


    @Resource
    PoolUserInvitationRewardMapper mapper;

    @DBAnno("dbRead")
    @Override
    public List<PoolUserInvitationRewardBean> getPoolUserInvitationRewardBeans(long uidBinance, Long algoId, Integer type, Long startDay, Long endDay) {
        Example example = new Example(PoolUserInvitationRewardBean.class);
        Example.Criteria criteria =  example.createCriteria()
                .andEqualTo("algoId", algoId)
                .andEqualTo("uidBinance", uidBinance)
                .andEqualTo("status", 0);
        if(type != null){
            criteria.andEqualTo("type",type);
        }
        if(startDay!=null){
            criteria.andGreaterThanOrEqualTo("day",startDay);
        }
        if(endDay!=null){
            criteria.andLessThanOrEqualTo("day",endDay);
        }


        String order = String.format(" day desc ");
        example.setOrderByClause(order);
        return mapper.selectByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolUserInvitationRewardBean> getPoolUserInvitationRewardInvitationsBeans(long uidPoolBinanceInvitation, Long algoId, Integer type, Long startDay, Long endDay) {
        Example example = new Example(PoolUserInvitationRewardBean.class);
        Example.Criteria criteria =  example.createCriteria()
                .andEqualTo("algoId", algoId)
                .andEqualTo("uidBinanceInvitation", uidPoolBinanceInvitation)
                .andEqualTo("status", 0);
        if(type != null){
            criteria.andEqualTo("type",type);
        }
        if(startDay!=null){
            criteria.andGreaterThanOrEqualTo("day",startDay);
        }
        if(endDay!=null){
            criteria.andLessThanOrEqualTo("day",endDay);
        }


        String order = String.format(" day desc ");
        example.setOrderByClause(order);
        return mapper.selectByExample(example);
    }
}
