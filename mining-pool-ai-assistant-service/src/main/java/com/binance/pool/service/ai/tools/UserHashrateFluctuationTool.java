package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.coinstats.StatsUsersDayBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-2(续): 用户算力波动分析 Tool
 * 分析用户一段时间内的算力变化趋势
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserHashrateFluctuationTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "分析矿池用户一段时间内的算力波动趋势。通过对比历史算力数据，分析日均算力、波动率、最高/最低值、趋势方向等。可用于排查算力下降原因。")
    public String analyzeUserHashrateFluctuation(
            @ToolParam(description = "矿池用户名，如 pool_user_888") String poolUsername,
            @ToolParam(description = "开始日期，格式YYYYMMDD") Long startDay,
            @ToolParam(description = "结束日期，格式YYYYMMDD") Long endDay) {
        log.info("AI Tool: analyzeUserHashrateFluctuation, poolUsername={}, startDay={}, endDay={}", poolUsername, startDay, endDay);
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
            result.put("puid", user.getId());

            // 2. 查时间范围内的算力数据
            List<StatsUsersDayBean> statsList = aiDataQueryService.getUserHashrateByRange(user.getId(), startDay, endDay);

            if (statsList == null || statsList.isEmpty()) {
                result.put("success", true);
                result.put("message", "该时间范围内无算力数据");
                return JSON.toJSONString(result);
            }

            // 3. 计算统计指标
            List<Map<String, Object>> dailyData = new ArrayList<>();
            BigDecimal totalHashrate = BigDecimal.ZERO;
            BigDecimal maxHashrate = BigDecimal.ZERO;
            BigDecimal minHashrate = new BigDecimal(Long.MAX_VALUE);
            Long maxDay = null, minDay = null;

            for (StatsUsersDayBean stats : statsList) {
                BigDecimal hashrate = stats.getShareAccept();
                totalHashrate = totalHashrate.add(hashrate);

                if (hashrate.compareTo(maxHashrate) > 0) {
                    maxHashrate = hashrate;
                    maxDay = stats.getDay();
                }
                if (hashrate.compareTo(minHashrate) < 0) {
                    minHashrate = hashrate;
                    minDay = stats.getDay();
                }

                dailyData.add(Map.of(
                        "day", stats.getDay(),
                        "shareAccept", stats.getShareAccept(),
                        "earn", stats.getEarn(),
                        "rejectRate", stats.getRejectRate() != null ? stats.getRejectRate() : 0
                ));
            }

            BigDecimal avgHashrate = totalHashrate.divide(BigDecimal.valueOf(statsList.size()), 4, RoundingMode.HALF_UP);

            // 计算波动率（标准差/均值）
            BigDecimal sumSquaredDiff = BigDecimal.ZERO;
            for (StatsUsersDayBean stats : statsList) {
                BigDecimal diff = stats.getShareAccept().subtract(avgHashrate);
                sumSquaredDiff = sumSquaredDiff.add(diff.multiply(diff));
            }
            double volatility = Math.sqrt(sumSquaredDiff.divide(BigDecimal.valueOf(statsList.size()), 8, RoundingMode.HALF_UP).doubleValue());
            double volatilityRate = avgHashrate.compareTo(BigDecimal.ZERO) != 0
                    ? volatility / avgHashrate.doubleValue() * 100 : 0;

            // 首尾对比
            BigDecimal firstHashrate = statsList.get(0).getShareAccept();
            BigDecimal lastHashrate = statsList.get(statsList.size() - 1).getShareAccept();
            double overallChangeRate = firstHashrate.compareTo(BigDecimal.ZERO) != 0
                    ? lastHashrate.subtract(firstHashrate).divide(firstHashrate, 4, RoundingMode.HALF_UP).doubleValue() * 100
                    : 0;

            result.put("dailyData", dailyData);
            result.put("summary", Map.of(
                    "avgHashrate", avgHashrate,
                    "maxHashrate", maxHashrate,
                    "maxDay", maxDay,
                    "minHashrate", minHashrate,
                    "minDay", minDay,
                    "volatilityRate", String.format("%.2f%%", volatilityRate),
                    "overallChangeRate", String.format("%.2f%%", overallChangeRate),
                    "trend", overallChangeRate > 5 ? "上升" : overallChangeRate < -5 ? "下降" : "平稳",
                    "dataPoints", statsList.size()
            ));

            result.put("startDay", startDay);
            result.put("endDay", endDay);
            result.put("success", true);
        } catch (Exception e) {
            log.error("analyzeUserHashrateFluctuation error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
