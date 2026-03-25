package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolSavingsRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
public interface PoolSavingsRequestDao {


    PoolSavingsRequest getPoolSavingsRequest(Long uidPoolBinance, Long poolSavingsId,Integer type);



}
