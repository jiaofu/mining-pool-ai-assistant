package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolUserHashMonitor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by yyh on 2020/5/13
 */
@org.apache.ibatis.annotations.Mapper
public interface PoolUserHashMonitorMapper extends Mapper<PoolUserHashMonitor> {

    @Insert({
            "<script>",
            "INSERT INTO pool_user_hash_monitor(puid,pool_username,uid_pool_binance,",
            "algo_id,hash_rate,day_hash_rate,hash_wave,regions) VALUES ",

            "<foreach collection=\"recordList\"  index=\"index\" item=\"record\" separator=\",\"> ",
            "(#{record.puid,jdbcType=BIGINT},#{record.poolUsername,jdbcType=VARCHAR},",
            "#{record.uidPoolBinance,jdbcType=BIGINT},#{record.algoId,jdbcType=BIGINT},",
            "#{record.hashRate,jdbcType=DECIMAL},#{record.dayHashRate,jdbcType=DECIMAL},",
            "#{record.hashWave,jdbcType=DECIMAL},#{record.regions,jdbcType=VARCHAR})</foreach>",

            " ON DUPLICATE KEY UPDATE puid=VALUES(puid),pool_username=VALUES(pool_username),",
            "uid_pool_binance=VALUES(uid_pool_binance),algo_id=VALUES(algo_id),hash_rate=VALUES(hash_rate),",
            "day_hash_rate=VALUES(day_hash_rate),hash_wave=VALUES(hash_wave),regions=VALUES(regions)",
            "</script>"
    })
    int batchInsert(@Param("recordList") List<PoolUserHashMonitor> monitors);
}
