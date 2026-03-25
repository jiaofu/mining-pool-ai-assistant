package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.mapper.pool.PoolSavingsAssetsMapper;
import com.binance.pool.dao.pool.PoolSavingsAssetsDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
@Repository
@Slf4j
public class PoolSavingsAssetsDaoImpl implements PoolSavingsAssetsDao {
    @Autowired
    private PoolSavingsAssetsMapper assetsMapper;



    @Override
    @DBAnno(value = "dbRead")
    public List<PoolSavingsAssets> getPoolSavingsAssets(long uidPoolBinance) {
        Example example = new Example(PoolSavingsAssets.class);
        example.createCriteria().andEqualTo("uidPoolBinance",uidPoolBinance).andEqualTo("puid",0);
        return assetsMapper.selectByExample(example);
    }


}
