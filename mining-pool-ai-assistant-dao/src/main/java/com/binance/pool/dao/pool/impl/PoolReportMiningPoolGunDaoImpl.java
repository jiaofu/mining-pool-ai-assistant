package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.mapper.pool.PoolReportMiningPoolGunMapper;
import com.binance.pool.dao.pool.PoolReportMiningPoolGunDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class PoolReportMiningPoolGunDaoImpl implements PoolReportMiningPoolGunDao {

    @Resource
    PoolReportMiningPoolGunMapper mapper;

    @Override
    public long insertPoolReportMiningPoolGunBean(PoolReportMiningPoolGunBean bean) {
        return mapper.insertSelective(bean );
    }

    @Override
    public PoolReportMiningPoolGunBean getPoolReportMiningPoolGunBean(long day) {
        Example example = new Example(PoolReportMiningPoolGunBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        return mapper.selectOneByExample(example);
    }

    @Override
    public PoolReportMiningPoolGunBean getLastBean() {
        Example example = new Example(PoolReportMiningPoolGunBean.class);
        Example.Criteria criteria = example.createCriteria();

        example.setOrderByClause("  day desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolGunBean> getGreaterThanOrEqualTo(Long day) {
        Example example = new Example(PoolReportMiningPoolGunBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(day != null){
            criteria .andGreaterThanOrEqualTo("day", day);
        }
        example.setOrderByClause("  day desc  ");
        return mapper.selectByExample(example);
    }
}
