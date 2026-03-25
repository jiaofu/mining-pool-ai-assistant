package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolCoinHashInfoBean;
import com.binance.pool.dao.mapper.pool.PoolCoinHashInfoMapper;
import com.binance.pool.dao.pool.CoinDao;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

@Repository
public class CoinDaoImpl implements CoinDao {


    @Resource
    PoolCoinHashInfoMapper poolCoinHashInfoMapper;
    /**
     * 获取币种信息
     * @param coinid
     * @return
     */
    @Override
    public PoolCoinHashInfoBean getCoinHashInfoBean(long coinid) {
        return poolCoinHashInfoMapper.getCoinHashInfoBean(coinid);
        //return null;
    }
}
