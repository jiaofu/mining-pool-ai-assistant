package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.coin.WorkerStatisticsBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by yyh on 2020/3/17
 */
@org.apache.ibatis.annotations.Mapper
public interface WorkerStatisticsMapper extends Mapper<WorkerStatisticsBean> {
    /**
     * 批量插入
     *
     * @param recordList
     * @return
     */
    @Insert({
            "<script>",
            "INSERT INTO worker_statistics_hour(puid,algo_id,worker_id,worker_name,",
            "hour,hash_rate,day_hash_rate,day_reject_rate,reject_rate,",
            "total_num,valid_num,invalid_num,created_date,modify_date) VALUES ",
            "<foreach collection=\"recordList\"  index=\"index\" item=\"record\" separator=\",\"> ",
            "(#{record.puid,jdbcType=BIGINT},#{record.algoId,jdbcType=INTEGER},",
            "#{record.workerId,jdbcType=BIGINT},#{record.workerName,jdbcType=VARCHAR},",
            "#{record.hour,jdbcType=BIGINT},#{record.hashRate,jdbcType=BIGINT},",
            "#{record.dayHashRate,jdbcType=BIGINT},#{record.dayRejectRate,jdbcType=DECIMAL},",
            "#{record.rejectRate,jdbcType=DECIMAL},",
            "#{record.totalNum,jdbcType=BIGINT},#{record.validNum,jdbcType=BIGINT},",
            "#{record.invalidNum,jdbcType=BIGINT},#{record.createdDate,jdbcType=TIMESTAMP},",
            "#{record.modifyDate,jdbcType=TIMESTAMP})</foreach>",
            " ON DUPLICATE KEY UPDATE puid=VALUES(puid),algo_id=VALUES(algo_id),",
            "worker_id=VALUES(worker_id),worker_name=VALUES(worker_name),hour=VALUES(hour),",
            "hash_rate=VALUES(hash_rate),day_hash_rate=VALUES(day_hash_rate),day_reject_rate=VALUES(day_reject_rate),",
            "reject_rate=VALUES(reject_rate),total_num=VALUES(total_num),valid_num=VALUES(valid_num),",
            "invalid_num=VALUES(invalid_num),created_date=VALUES(created_date),",
            "modify_date=VALUES(modify_date)",
            "</script>"
    })


    Integer batchInsert(@Param("recordList") List<WorkerStatisticsBean> recordList);
}
