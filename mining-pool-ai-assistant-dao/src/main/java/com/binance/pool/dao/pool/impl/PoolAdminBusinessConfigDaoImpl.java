package com.binance.pool.dao.pool.impl;


import com.binance.pool.base.bean.PoolAdminBusinessConfigBean;
import com.binance.pool.base.mapper.PoolAdminBusinessConfigMapper;
import com.binance.pool.dao.pool.PoolAdminBusinessConfigDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository
public class PoolAdminBusinessConfigDaoImpl implements PoolAdminBusinessConfigDao {

    @Resource
    PoolAdminBusinessConfigMapper mapper;

    @Override
    public PoolAdminBusinessConfigBean getPoolSendAdminMsgByCode(String code) {
        Example example = new Example(PoolAdminBusinessConfigBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("status",0);

        criteria.andEqualTo("code",code);

        return mapper.selectOneByExample(example);
    }
}
