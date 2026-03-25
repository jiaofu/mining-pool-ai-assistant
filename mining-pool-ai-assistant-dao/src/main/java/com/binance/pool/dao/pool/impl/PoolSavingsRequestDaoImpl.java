package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolSavingsRequest;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.mapper.pool.PoolSavingsRequestMapper;
import com.binance.pool.dao.pool.PoolSavingsRequestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
@Repository
public class PoolSavingsRequestDaoImpl implements PoolSavingsRequestDao {
    @Autowired
    PoolSavingsRequestMapper requestMapper;


    @DBAnno("dbRead")
    @Override
    public PoolSavingsRequest getPoolSavingsRequest(Long uidPoolBinance, Long poolSavingsId, Integer type) {
        Example example = new Example(PoolSavingsRequest.class);
        example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("poolSavingsId", poolSavingsId)
                .andEqualTo("type", type);

        String order = String.format(" id desc limit 1");
        example.setOrderByClause(order);
        return requestMapper.selectOneByExample(example);
    }


}
