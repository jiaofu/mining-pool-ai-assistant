package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.mapper.pool.PoolCoinMapper;
import com.binance.pool.dao.pool.PoolCoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author yyh
 * @date 2020/3/13
 */
@Repository
public class PoolCoinDaoImpl implements PoolCoinDao {
    @Autowired
    PoolCoinMapper coinMapper;

    @Override
    public PoolCoinBean findCoinById(Long coinId) {
        Example example = new Example(PoolCoinBean.class);
        example.createCriteria()
                .andEqualTo("id",coinId)
        ;
        return coinMapper.selectOneByExample(example);
    }

    @Override
    public List<PoolCoinBean> findStatusCoin(Integer status) {
        Example example = new Example(PoolCoinBean.class);
        example.createCriteria()
                .andEqualTo("status",status)
        ;
        return coinMapper.selectByExample(example);
    }

    @Override
    public List<PoolCoinBean> findTypeCoin(Integer coinType) {
        Example example = new Example(PoolCoinBean.class);
        example.createCriteria()
                .andEqualTo("coinType",coinType)
                .andEqualTo("status",0)
        ;
        return coinMapper.selectByExample(example);
    }
}
