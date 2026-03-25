package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import com.binance.pool.dao.mapper.pool.PoolMiningWorkersMapper;
import com.binance.pool.dao.mapper.pool.PoolRequestApiConfigMapper;
import com.binance.pool.dao.pool.PoolRequestApiConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Repository
public class PoolRequestApiConfigDaoImpl implements PoolRequestApiConfigDao {

    @Autowired
    PoolRequestApiConfigMapper mapper;


    @Override
    public PoolRequestApiConfigBean getPoolRequestApiConfigBeans(String appName) {
        Example example = new Example(PoolRequestApiConfigBean.class);
        example.createCriteria()
                .andEqualTo("status",0).andEqualTo("appName",appName);
        return mapper.selectOneByExample(example);
    }
}
