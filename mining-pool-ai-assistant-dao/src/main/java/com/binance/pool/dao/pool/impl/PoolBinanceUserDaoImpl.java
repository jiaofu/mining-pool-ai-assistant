package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.mapper.pool.BinanceUserMapper;
import com.binance.pool.dao.pool.PoolBinanceUserDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;

import java.util.List;

@Repository
public class PoolBinanceUserDaoImpl implements PoolBinanceUserDao {
    @Resource
    BinanceUserMapper binanceUserMapper;
    @Override
    public long insertPoolBinanceUserBean(PoolBinanceUserBean poolBinanceUserBean) {
        return binanceUserMapper.insertSelective(poolBinanceUserBean);
    }

    @Override
    public PoolBinanceUserBean getPoolBinanceUserBeanByUid(long uid) {
        Example example = new Example(PoolBinanceUserBean.class);
        example.createCriteria().andEqualTo("uidBinance",uid);
        return binanceUserMapper.selectOneByExample(example);
    }


    @Override
    public PoolBinanceUserBean getPoolBinanceUserBeanByuidPoolBinance(long uidPoolBinance) {
        Example example = new Example(PoolBinanceUserBean.class);
        example.createCriteria().andEqualTo("uidPoolBinance",uidPoolBinance);
        return binanceUserMapper.selectOneByExample(example);
    }
    @Override
    public List<PoolBinanceUserBean> getPoolBinanceUserBeans(List<Long> uidPoolBinances) {
        Example example = new Example(PoolBinanceUserBean.class);
        example.createCriteria().andIn("uidPoolBinance",uidPoolBinances);
        return binanceUserMapper.selectByExample(example);
    }
}
