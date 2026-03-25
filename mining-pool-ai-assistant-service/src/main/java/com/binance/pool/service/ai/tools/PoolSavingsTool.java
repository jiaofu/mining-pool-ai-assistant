package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-7: 矿池宝信息查询 Tool
 * 查看用户的矿池宝理财产品情况
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PoolSavingsTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "查询用户的矿池宝理财产品信息，包括存入金额、利息收入、年化收益率等。同时返回可用的矿池宝产品列表。")
    public String queryPoolSavings(
            @ToolParam(description = "矿池用户名，如 pool_user_888") String poolUsername) {
        log.info("AI Tool: queryPoolSavings, poolUsername={}", poolUsername);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 查用户
            PoolUserBean user = aiDataQueryService.getUserByPoolUsername(poolUsername);
            if (user == null) {
                result.put("success", false);
                result.put("error", "未找到用户: " + poolUsername);
                return JSON.toJSONString(result);
            }

            result.put("poolUsername", poolUsername);
            result.put("miningBaoStatus", user.getMiningBaoStatus() != null ? user.getMiningBaoStatus() : 0);

            // 2. 查矿池宝资产
            List<PoolSavingsAssets> assets = aiDataQueryService.getUserSavingsAssets(user.getUidBinance());
            if (assets != null && !assets.isEmpty()) {
                BigDecimal totalPending = BigDecimal.ZERO;
                BigDecimal totalInterest = BigDecimal.ZERO;

                List<Map<String, Object>> assetDetails = new java.util.ArrayList<>();
                for (PoolSavingsAssets asset : assets) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("coinId", asset.getCoinId());
                    detail.put("pendingAmount", asset.getPendingAmount());
                    detail.put("pendingAmountInterest", asset.getPendingAmountInterest());
                    detail.put("totalInterestAmount", asset.getTotalInterestAmount());
                    detail.put("totalApplyAmount", asset.getTotalApplyAmount());
                    detail.put("totalRedemptionAmount", asset.getTotalRedemptionAmount());
                    detail.put("day", asset.getDay());
                    assetDetails.add(detail);

                    if (asset.getPendingAmount() != null) totalPending = totalPending.add(asset.getPendingAmount());
                    if (asset.getTotalInterestAmount() != null) totalInterest = totalInterest.add(asset.getTotalInterestAmount());
                }

                result.put("savingsAssets", assetDetails);
                result.put("totalPendingAmount", totalPending);
                result.put("totalInterestAmount", totalInterest);
            } else {
                result.put("savingsAssets", List.of());
                result.put("message", "该用户没有矿池宝资产");
            }

            // 3. 查利息详情
            PoolReportSavingsInterestBean interest = aiDataQueryService.getUserSavingsInterest(user.getUidBinance());
            if (interest != null) {
                result.put("interestReport", Map.of(
                        "yesterdayAmount", interest.getYesterdayAmount(),
                        "dayThirtyAmount", interest.getDayThirtyAmount(),
                        "monthAmount", interest.getMonthAmount(),
                        "seasonAmount", interest.getSeasonAmount(),
                        "yearAmount", interest.getYearAmount(),
                        "totalAmount", interest.getTotalAmount(),
                        "day", interest.getDay()
                ));
            }

            // 4. 查可用产品
            List<PoolSavingsProduct> products = aiDataQueryService.getActiveSavingsProducts();
            if (products != null && !products.isEmpty()) {
                result.put("availableProducts", products.stream().map(p -> Map.of(
                        "id", p.getId(),
                        "name", p.getName(),
                        "coinId", p.getCoinId(),
                        "annualizedRate", p.getAnnualizedRate() != null ? p.getAnnualizedRate() : BigDecimal.ZERO,
                        "userLimit", p.getUserLimit() != null ? p.getUserLimit() : BigDecimal.ZERO,
                        "remainingAmount", p.getRemainingAmount() != null ? p.getRemainingAmount() : BigDecimal.ZERO
                )).collect(java.util.stream.Collectors.toList()));
            }

            result.put("success", true);
        } catch (Exception e) {
            log.error("queryPoolSavings error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
