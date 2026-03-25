package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolSavingsRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
@org.apache.ibatis.annotations.Mapper
public interface PoolSavingsRequestMapper extends Mapper<PoolSavingsRequest> {


    @Select({
            "<script>",
            "select sum(amount)",
            "from pool_savings_request",
            "where pool_savings_id=#{poolSavingsId} and day=#{day} and status=#{status} and type in",
            "<foreach collection='types' item='type' open='(' separator=',' close=')'>",
            "#{type}",
            "</foreach>",
            "</script>"
    })
    BigDecimal getPoolSavingsProductById(@Param("poolSavingsId") Long poolSavingsId,@Param("day") Long day ,@Param("status") Integer status, @Param("types") List<Integer> types);
}
