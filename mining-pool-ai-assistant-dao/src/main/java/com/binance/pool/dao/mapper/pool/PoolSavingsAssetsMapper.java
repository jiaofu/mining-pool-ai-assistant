package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolSavingsAssets;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by yyh on 2020/8/21
 */
@org.apache.ibatis.annotations.Mapper
public interface PoolSavingsAssetsMapper extends Mapper<PoolSavingsAssets> {

    @Insert({
            "<script>",
            "INSERT INTO pool_savings_assets(puid,uid_pool_binance,pool_savings_id,",
            "day,coin_id,pending_amount,pending_amount_interest,total_interest_amount," ,
            "total_apply_amount,total_redemption_amount,created_date,modify_date) VALUES ",

            "<foreach collection=\"recordList\"  index=\"index\" item=\"record\" separator=\",\"> ",
            "(#{record.puid,jdbcType=BIGINT},#{record.uidPoolBinance,jdbcType=BIGINT},",
            "#{record.poolSavingsId,jdbcType=BIGINT},#{record.day,jdbcType=BIGINT},",
            "#{record.coinId,jdbcType=BIGINT},#{record.pendingAmount,jdbcType=DECIMAL},",
            "#{record.pendingAmountInterest,jdbcType=DECIMAL},#{record.totalInterestAmount,jdbcType=DECIMAL},",
            "#{record.totalApplyAmount,jdbcType=DECIMAL},#{record.totalRedemptionAmount,jdbcType=DECIMAL},",
            "#{record.createdDate,jdbcType=TIMESTAMP},#{record.modifyDate,jdbcType=TIMESTAMP})</foreach>",

            " ON DUPLICATE KEY UPDATE puid=VALUES(puid),uid_pool_binance=VALUES(uid_pool_binance),",
            "pool_savings_id=VALUES(pool_savings_id),day=VALUES(day),",
            "coin_id=VALUES(coin_id),pending_amount=VALUES(pending_amount),",
            "pending_amount_interest=VALUES(pending_amount_interest),",
            "total_interest_amount=VALUES(total_interest_amount),",
            "total_apply_amount=VALUES(total_apply_amount),",
            "total_redemption_amount=VALUES(total_redemption_amount),",
            "created_date=VALUES(created_date),modify_date=VALUES(modify_date)",
            "</script>"
    })
    int batchInsert(@Param("recordList") List<PoolSavingsAssets> temps);

    @Select("select max(day) from pool_savings_assets limit 1;")
    Long getMaxInterestDay();


    @Select("SELECT SUM(pending_amount+pending_amount_interest) FROM pool_savings_assets where pool_savings_id=#{poolSavingsId}")
    BigDecimal getAmount(@Param("poolSavingsId") Long poolSavingsId);
}
