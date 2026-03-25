package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;
import com.binance.pool.dao.mapper.pool.PoolReportMiningPoolPartnerMapper;
import com.binance.pool.dao.pool.PoolReportMiningPoolPartnerDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class PoolReportMiningPoolPartnerDaoImpl implements PoolReportMiningPoolPartnerDao {
    @Resource
    PoolReportMiningPoolPartnerMapper mapper;

    @Override
    public long insertPoolReportMiningPoolPartner(PoolReportMiningPoolPartnerBean bean) {
        return mapper.insertSelective(bean );
    }

    @Override
    public PoolReportMiningPoolPartnerBean getPoolReportMiningPoolPartnerBean(long day, String coinName) {
        Example example = new Example(PoolReportMiningPoolPartnerBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);
        criteria .andEqualTo("coinName", coinName);
        return mapper.selectOneByExample(example);
    }

    @Override
    public PoolReportMiningPoolPartnerBean getLastBean(String coinName) {
        Example example = new Example(PoolReportMiningPoolPartnerBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("coinName", coinName);

        example.setOrderByClause("  day desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolPartnerBean> getPoolReportMiningPoolPartnerBeans(long day) {
        Example example = new Example(PoolReportMiningPoolPartnerBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria .andEqualTo("day", day);

        return mapper.selectByExample(example);
    }

    @Override
    public List<PoolReportMiningPoolPartnerBean> getGreaterThanOrEqualTo(Long day) {
        Example example = new Example(PoolReportMiningPoolPartnerBean.class);
        Example.Criteria criteria = example.createCriteria();
        if(day != null){
            criteria.andGreaterThanOrEqualTo("day", day);
        }
        example.setOrderByClause("  day desc ");
        return mapper.selectByExample(example);
    }
}
