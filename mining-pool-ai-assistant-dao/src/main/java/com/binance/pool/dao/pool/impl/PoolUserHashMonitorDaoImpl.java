package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolUserHashMonitor;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.mapper.pool.PoolUserHashMonitorMapper;
import com.binance.pool.dao.pool.PoolUserHashMonitorDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by yyh on 2020/5/13
 */
@Service
@Slf4j
public class PoolUserHashMonitorDaoImpl implements PoolUserHashMonitorDao {

    @Autowired
    PoolUserHashMonitorMapper monitorMapper;


    @DBAnno("dbRead")
    @Override
    public List<PoolUserHashMonitor> getPoolUserHashMonitors(List<Long> uidPoolList,Long algoId) {
        Example example = new Example(PoolUserHashMonitor.class);
        Example.Criteria criteria = example.createCriteria()
                .andIn("uidPoolBinance",uidPoolList)
                .andEqualTo("algoId",algoId)
                .andGreaterThan("puid",0);


        return monitorMapper.selectByExample(example);
    }

    @Override
    public PoolUserHashMonitor getPoolUserHashMonitors(long uidPoolBinance, Date lastShareTime) {
        Example example = new Example(PoolUserHashMonitor.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance",uidPoolBinance)
                .andGreaterThan("puid",0)
                .andGreaterThanOrEqualTo("lastShareTime",lastShareTime);

        String order = String.format(" algo_id asc limit 1");
        example.setOrderByClause(order);
        return monitorMapper.selectOneByExample(example);
    }

}
