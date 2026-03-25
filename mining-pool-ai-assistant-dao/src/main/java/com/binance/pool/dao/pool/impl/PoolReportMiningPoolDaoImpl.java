package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.mapper.pool.PoolReportMiningPoolMapper;
import com.binance.pool.dao.pool.PoolReportMiningPoolDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class PoolReportMiningPoolDaoImpl implements PoolReportMiningPoolDao {
    @Resource
    PoolReportMiningPoolMapper mapper;


    @Override
    public long insertPoolReportMiningPoolBean(PoolReportMiningPoolBean bean) {
        return mapper.insertSelective(bean );
    }

    @Override
    public PoolReportMiningPoolBean getPoolReportMiningPoolBean(long day, String coinName) {
        Example example = new Example(PoolReportMiningPoolBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        criteria .andEqualTo("coinName", coinName);
        return mapper.selectOneByExample(example);
    }

    @Override
    public PoolReportMiningPoolBean getLastBean(String coinName) {
        Example example = new Example(PoolReportMiningPoolBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("coinName", coinName);

        example.setOrderByClause("  day desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolBean> getPoolReportMiningPoolBeans(long day) {
        Example example = new Example(PoolReportMiningPoolBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        return mapper.selectByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolBean> getGreaterThanOrEqualTo(Long day) {
        Example example = new Example(PoolReportMiningPoolBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(day != null){
            criteria .andGreaterThanOrEqualTo("day", day);
        }
        example.setOrderByClause("  day desc  ");
        return mapper.selectByExample(example);
    }
}
