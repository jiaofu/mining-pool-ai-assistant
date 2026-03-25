package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolSavingsProduct;

import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
public interface PoolSavingsProductDao {


    /**
     * 事务
     * @param id
     * @return
     */
    PoolSavingsProduct getPoolSavingsProductById(long id);

    List<PoolSavingsProduct> getPoolSavingsProducts(long algoId);
}
