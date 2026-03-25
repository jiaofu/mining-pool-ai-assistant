package com.binance.pool.service.pool;

import com.binance.pool.base.bean.PoolBillingLogBean;
import com.binance.pool.base.bean.PoolPriceHashrateBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.PoolUserBillBean;
import com.binance.pool.dao.bean.coinstats.StatsPoolDayBean;
import com.binance.pool.dao.bean.coinstats.StatsUsersDayBean;

import java.util.List;
import java.util.Map;

/**
 * AI 数据查询服务
 * 为 AI Tool 提供数据库查询能力
 */
public interface AiDataQueryService {

    // ========== 用户相关 ==========

    /**
     * 根据矿池用户名查找用户
     */
    PoolUserBean getUserByPoolUsername(String poolUsername);

    /**
     * 根据 puid 查找用户
     */
    PoolUserBean getUserByPuid(Long puid);

    // ========== 账单相关 ==========

    /**
     * 查某天的出账状态日志
     */
    List<PoolBillingLogBean> getBillingLogByDay(Long day);

    /**
     * 查某天某算法的出账状态
     */
    PoolBillingLogBean getBillingLogByDayAndAlgo(Long day, Long algoId);

    /**
     * 查某段时间的出账日志
     */
    List<PoolBillingLogBean> getBillingLogByDayRange(Long startDay, Long endDay);

    /**
     * 查某天的账单数量（按算法分组）
     */
    List<Map<String, Object>> getBillCountByDay(Long day);

    /**
     * 查用户某段时间的账单明细
     */
    List<PoolUserBillBean> getUserBillsByRange(Long puid, Long coinId, Long startDay, Long endDay);

    /**
     * 查用户某段时间的账单汇总
     */
    List<PoolUserBillBean> getUserBillsSummary(Long puid, Long startDay, Long endDay);

    /**
     * 查某天账单数量减少最多的主账户（与前一天对比）
     */
    List<Map<String, Object>> getTopDecreasedBillUsers(Long day, Long previousDay, int topN);

    // ========== 算力相关 ==========

    /**
     * 查用户某天算力
     */
    StatsUsersDayBean getUserHashrateByDay(Long puid, Long day);

    /**
     * 查用户某段时间算力趋势
     */
    List<StatsUsersDayBean> getUserHashrateByRange(Long puid, Long startDay, Long endDay);

    /**
     * 查矿池某天算力
     */
    StatsPoolDayBean getPoolHashrateByDay(Long day);

    /**
     * 查矿池某段时间算力趋势
     */
    List<StatsPoolDayBean> getPoolHashrateByRange(Long startDay, Long endDay);

    /**
     * 查算力变化最大的 TopN 用户
     */
    List<Map<String, Object>> getTopHashrateChangeUsers(Long today, Long yesterday, int topN);

    // ========== 矿池报告 ==========

    /**
     * 查矿池某天的报告
     */
    List<PoolReportMiningPoolBean> getPoolReportByDay(Long day);

    // ========== 价格收益 ==========

    /**
     * 查最新币种价格和每T收益
     */
    PoolPriceHashrateBean getLatestCoinPrice(Long coinId);

    /**
     * 查全部币种最新价格
     */
    List<PoolPriceHashrateBean> getLatestAllCoinPrice();

    /**
     * 查某币种历史价格
     */
    List<PoolPriceHashrateBean> getCoinPriceByRange(Long coinId, Long startDay, Long endDay);

    // ========== 矿池宝 ==========

    /**
     * 查用户矿池宝资产
     */
    List<PoolSavingsAssets> getUserSavingsAssets(Long uidPoolBinance);

    /**
     * 查用户矿池宝利息
     */
    PoolReportSavingsInterestBean getUserSavingsInterest(Long uidPoolBinance);

    /**
     * 查矿池宝产品列表
     */
    List<PoolSavingsProduct> getActiveSavingsProducts();
}
