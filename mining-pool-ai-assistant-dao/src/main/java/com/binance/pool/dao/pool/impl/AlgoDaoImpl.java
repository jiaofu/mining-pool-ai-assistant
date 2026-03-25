package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.mapper.pool.AlgoMapper;
import com.binance.pool.dao.pool.AlgoDao;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author yyh
 * @date 2020/3/11
 */
@Repository
public class AlgoDaoImpl implements AlgoDao {

    @Resource
    AlgoMapper algoMapper;
    @Override
    public List<PoolAlgoBean> getPoolAlgoBeans(int status) {
        Example example = new Example(PoolAlgoBean.class);
        example.createCriteria().andEqualTo("status",status);
        return algoMapper.selectByExample(example);
    }

    @Override
    public PoolAlgoBean findById(Long algoId) {
        PoolAlgoBean algo = new PoolAlgoBean();
        algo.setId(algoId);
        return algoMapper.selectByPrimaryKey(algo);
    }
}
