package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;
import com.binance.pool.base.mapper.PoolReportMiningPoolSelfMapper;
import com.binance.pool.dao.pool.PoolReportMiningPoolSelfDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class PoolReportMiningPoolSelfDaoImpl  implements PoolReportMiningPoolSelfDao {

    @Resource
    PoolReportMiningPoolSelfMapper mapper;

    @Override
    public long insertPoolReportMiningPoolSelf(PoolReportMiningPoolSelfBean bean) {
        return mapper.insertSelective(bean );
    }

    @Override
    public PoolReportMiningPoolSelfBean getPoolReportMiningPoolSelfBean(long day, String coinName) {
        Example example = new Example(PoolReportMiningPoolSelfBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        criteria .andEqualTo("coinName", coinName);
        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolSelfBean> getPoolReportMiningPoolSelfBean(long day) {
        Example example = new Example(PoolReportMiningPoolSelfBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        return mapper.selectByExample(example);
    }

    @Override
    public PoolReportMiningPoolSelfBean getLastBean(String coinName) {
        Example example = new Example(PoolReportMiningPoolSelfBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("coinName", coinName);

        example.setOrderByClause("  day desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolSelfBean> getGreaterThanOrEqualTo(Long day) {
        Example example = new Example(PoolReportMiningPoolSelfBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(day != null){
            criteria .andGreaterThanOrEqualTo("day", day);
        }
        example.setOrderByClause("  day desc ");
        return mapper.selectByExample(example);
    }
}
