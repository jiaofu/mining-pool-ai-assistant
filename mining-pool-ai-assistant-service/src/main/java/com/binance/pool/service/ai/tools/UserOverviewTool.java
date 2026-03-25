package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.base.bean.PoolPriceHashrateBean;
import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.PoolUserBillBean;
import com.binance.pool.dao.bean.coinstats.StatsUsersDayBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-4: 用户综合信息查询 Tool
 * 一站式拉取用户多维度数据
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserOverviewTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "一站式查询矿池用户的综合信息，包括：当前算力和矿机状态、最近7天账单汇总、矿池宝资产情况、币种收益率参考。适合客服接到用户投诉时快速了解全面情况。")
    public String queryUserOverview(
            @ToolParam(description = "矿池用户名，如 pool_user_888") String poolUsername) {
        log.info("AI Tool: queryUserOverview, poolUsername={}", poolUsername);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 查用户基本信息
            PoolUserBean user = aiDataQueryService.getUserByPoolUsername(poolUsername);
            if (user == null) {
                result.put("success", false);
                result.put("error", "未找到用户: " + poolUsername);
                return JSON.toJSONString(result);
            }

            result.put("userInfo", Map.of(
                    "poolUsername", user.getPoolUsername(),
                    "puid", user.getId(),
                    "uidBinance", user.getUidBinance(),
                    "status", user.getStatus(),
                    "miningBaoStatus", user.getMiningBaoStatus() != null ? user.getMiningBaoStatus() : 0
            ));

            Long yesterday = Long.parseLong(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            Long weekAgo = Long.parseLong(LocalDate.now().minusDays(7).format(DateTimeFormatter.ofPattern("yyyyMMdd")));

            // 2. 当前算力
            StatsUsersDayBean hashrate = aiDataQueryService.getUserHashrateByDay(user.getId(), yesterday);
            if (hashrate != null) {
                result.put("currentHashrate", Map.of(
                        "day", yesterday,
                        "shareAccept", hashrate.getShareAccept(),
                        "shareStale", hashrate.getShareStale(),
                        "shareReject", hashrate.getShareReject(),
                        "rejectRate", hashrate.getRejectRate() != null ? hashrate.getRejectRate() : 0,
                        "earn", hashrate.getEarn()
                ));
            }

            // 3. 最近7天账单
            List<PoolUserBillBean> recentBills = aiDataQueryService.getUserBillsSummary(user.getId(), weekAgo, yesterday);
            if (recentBills != null && !recentBills.isEmpty()) {
                BigDecimal totalEarn = recentBills.stream()
                        .filter(b -> b.getEarn() != null)
                        .map(PoolUserBillBean::getEarn)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalPayment = recentBills.stream()
                        .filter(b -> b.getUserPayment() != null)
                        .map(PoolUserBillBean::getUserPayment)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                result.put("recentBills", Map.of(
                        "period", weekAgo + " ~ " + yesterday,
                        "billCount", recentBills.size(),
                        "totalEarn", totalEarn,
                        "totalUserPayment", totalPayment
                ));
            }

            // 4. 矿池宝资产
            List<PoolSavingsAssets> savingsAssets = aiDataQueryService.getUserSavingsAssets(user.getUidBinance());
            if (savingsAssets != null && !savingsAssets.isEmpty()) {
                result.put("savingsAssets", savingsAssets.stream().map(s -> Map.of(
                        "coinId", s.getCoinId(),
                        "pendingAmount", s.getPendingAmount(),
                        "totalInterestAmount", s.getTotalInterestAmount(),
                        "totalApplyAmount", s.getTotalApplyAmount()
                )).collect(java.util.stream.Collectors.toList()));
            }

            // 5. 矿池宝利息
            PoolReportSavingsInterestBean interest = aiDataQueryService.getUserSavingsInterest(user.getUidBinance());
            if (interest != null) {
                result.put("savingsInterest", Map.of(
                        "yesterdayAmount", interest.getYesterdayAmount(),
                        "dayThirtyAmount", interest.getDayThirtyAmount(),
                        "monthAmount", interest.getMonthAmount(),
                        "totalAmount", interest.getTotalAmount()
                ));
            }

            // 6. 最新币价参考
            List<PoolPriceHashrateBean> prices = aiDataQueryService.getLatestAllCoinPrice();
            if (prices != null) {
                result.put("coinPrices", prices.stream().map(p -> Map.of(
                        "coinId", p.getCoinId(),
                        "price", p.getPrice(),
                        "eachHaveCoin", p.getEachHaveCoin() != null ? p.getEachHaveCoin() : BigDecimal.ZERO,
                        "eachHaveUsdt", p.getEachHaveUsdt() != null ? p.getEachHaveUsdt() : BigDecimal.ZERO,
                        "difficulty", p.getDifficulty() != null ? p.getDifficulty() : BigDecimal.ZERO
                )).collect(java.util.stream.Collectors.toList()));
            }

            result.put("success", true);
        } catch (Exception e) {
            log.error("queryUserOverview error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
