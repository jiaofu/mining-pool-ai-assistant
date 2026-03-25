package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.coinstats.StatsUsersDayBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StatsUsersDayMapper extends tk.mybatis.mapper.common.Mapper<StatsUsersDayBean> {

    /**
     * 查用户某天的算力统计
     */
    @Select("SELECT * FROM stats_users_day WHERE puid = #{puid} AND day = #{day}")
    StatsUsersDayBean getUserStatsByDay(@Param("puid") Long puid, @Param("day") Long day);

    /**
     * 查用户某段时间的算力趋势
     */
    @Select("SELECT * FROM stats_users_day WHERE puid = #{puid} AND day >= #{startDay} AND day <= #{endDay} ORDER BY day")
    List<StatsUsersDayBean> getUserStatsByDayRange(@Param("puid") Long puid, @Param("startDay") Long startDay, @Param("endDay") Long endDay);
}
