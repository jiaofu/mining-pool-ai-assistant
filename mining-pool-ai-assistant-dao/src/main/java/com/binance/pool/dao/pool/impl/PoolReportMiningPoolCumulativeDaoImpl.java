package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;
import com.binance.pool.base.mapper.PoolReportMiningPoolCumulativeMapper;
import com.binance.pool.dao.pool.PoolReportMiningPoolCumulativeDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class PoolReportMiningPoolCumulativeDaoImpl implements PoolReportMiningPoolCumulativeDao {

    @Resource
    PoolReportMiningPoolCumulativeMapper mapper;

    @Override
    public long insertPoolReportMiningPoolCumulative(PoolReportMiningPoolCumulativeBean bean) {
        return mapper.insertSelective(bean );
    }

    @Override
    public PoolReportMiningPoolCumulativeBean getPoolReportMiningPoolCumulativeBean(long day) {
        Example example = new Example(PoolReportMiningPoolCumulativeBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        return mapper.selectOneByExample(example);
    }

    @Override
    public PoolReportMiningPoolCumulativeBean getLastBean() {
        Example example = new Example(PoolReportMiningPoolCumulativeBean.class);

        example.setOrderByClause("  day desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolCumulativeBean> getGreaterThanOrEqualTo(Long day) {
        Example example = new Example(PoolReportMiningPoolCumulativeBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(day != null){
            criteria .andGreaterThanOrEqualTo("day", day);
        }
        example.setOrderByClause(" day desc  ");
        return mapper.selectByExample(example);
    }
}
