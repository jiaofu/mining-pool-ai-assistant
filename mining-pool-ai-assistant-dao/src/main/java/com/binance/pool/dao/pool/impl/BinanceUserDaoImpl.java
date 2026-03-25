package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.mapper.pool.BinanceUserMapper;
import com.binance.pool.dao.pool.BinanceUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by yyh on 2020/3/17
 */
@Repository
public class BinanceUserDaoImpl implements BinanceUserDao {
    @Autowired
    BinanceUserMapper binanceUserMapper;
    @Override
    public PoolBinanceUserBean getUidBinance(Long uidBinance) {
        return binanceUserMapper.getPoolBinanceUserId(uidBinance);
    }
}
