package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.coinstats.StatsPoolDayBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatsPoolDayMapper extends tk.mybatis.mapper.common.Mapper<StatsPoolDayBean> {

    /**
     * 查矿池某天的算力统计
     */
    @Select("SELECT * FROM stats_pool_day WHERE day = #{day}")
    StatsPoolDayBean getPoolStatsByDay(@Param("day") Long day);

    /**
     * 查矿池某段时间的算力趋势
     */
    @Select("SELECT * FROM stats_pool_day WHERE day >= #{startDay} AND day <= #{endDay} ORDER BY day")
    List<StatsPoolDayBean> getPoolStatsByDayRange(@Param("startDay") Long startDay, @Param("endDay") Long endDay);
}
