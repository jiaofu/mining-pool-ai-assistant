package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolCoinHashInfoBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@org.apache.ibatis.annotations.Mapper
public interface PoolCoinHashInfoMapper extends tk.mybatis.mapper.common.Mapper<PoolCoinHashInfoBean>{


    @Select("SELECT * FROM pool_coin_hash_info where coin_id=#{coinid}  order by id desc limit 1 ")
    PoolCoinHashInfoBean getCoinHashInfoBean(@Param("coinid") long coinid);
}
