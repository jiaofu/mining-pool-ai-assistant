package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.mapper.pool.PoolSavingsProductMapper;
import com.binance.pool.dao.pool.PoolSavingsProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
@Repository
public class PoolSavingsProductDaoImpl implements PoolSavingsProductDao {
    @Autowired
    private PoolSavingsProductMapper productMapper;

    @Override
    @DBAnno("dbRead")
    public PoolSavingsProduct getPoolSavingsProductById(long id) {
        Example example = new Example(PoolSavingsProduct.class);
        example.createCriteria()
                .andEqualTo("status",0)
                .andEqualTo("id",id);
        return productMapper.selectOneByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolSavingsProduct> getPoolSavingsProducts(long algoId) {
        Example example = new Example(PoolSavingsProduct.class);
        example.createCriteria()
                .andEqualTo("status",0)
                .andEqualTo("algoId",algoId);
        return productMapper.selectByExample(example);
    }
}
