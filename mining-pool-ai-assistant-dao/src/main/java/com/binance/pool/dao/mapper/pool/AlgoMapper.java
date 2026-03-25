package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolAlgoBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yyh
 * @date 2020/3/11
 */
@org.apache.ibatis.annotations.Mapper
public interface AlgoMapper extends Mapper<PoolAlgoBean> {

    @Select("SELECT * FROM pool_algo WHERE status = #{status} order by pool_index ")
    List<PoolAlgoBean> getPoolAlgoBeans(@Param("status") int status);
}
