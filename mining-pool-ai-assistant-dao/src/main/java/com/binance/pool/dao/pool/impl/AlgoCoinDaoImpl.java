package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.mapper.pool.AlgoCoinMapper;
import com.binance.pool.dao.mapper.pool.AlgoMapper;
import com.binance.pool.dao.mapper.pool.CoinMapper;
import com.binance.pool.dao.pool.AlgoCoinDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class AlgoCoinDaoImpl implements AlgoCoinDao {
    @Resource
    AlgoCoinMapper algoCoinMapper;

    @Resource
    CoinMapper coinMapper;
    @Resource
    AlgoMapper algoMapper;

    @Override
    public List<PoolAlgoBean> getPoolAlgoBeans(int status) {
        Example example = new Example(PoolAlgoBean.class);
        example.createCriteria().andEqualTo("status",status);
        return algoMapper.selectByExample(example);
    }
    @Override
    public List<PoolAlgoCoinBean> getPoolAlgoCoinBeans(int status) {
        return algoCoinMapper.getPoolAlgoCoinBeans(status);
    }


    @Override
    public List<PoolCoinBean> getPoolCoinBeans(int status) {
        return coinMapper.getPoolCoinBeans(status);
    }


    @Override
    public List<PoolAlgoCoinBean> getAlgoCoinByAlgo(long algoId,int status) {
        Example example = new Example(PoolAlgoCoinBean.class);
        example.createCriteria()
                .andEqualTo("status",status)
                .andEqualTo("algoId",algoId);
        return algoCoinMapper.selectByExample(example);
    }

    @Override
    public PoolAlgoCoinBean getAlgoCoinByCoin(int coinId) {
        Example example = new Example(PoolAlgoCoinBean.class);
        example.createCriteria()
                .andEqualTo("status",0)
                .andEqualTo("coinId",coinId);
        return algoCoinMapper.selectOneByExample(example);
    }
}
