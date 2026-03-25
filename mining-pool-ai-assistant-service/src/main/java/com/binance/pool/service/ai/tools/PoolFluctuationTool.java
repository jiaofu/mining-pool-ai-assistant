package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.coinstats.StatsPoolDayBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-3: 矿池整体算力波动分析 Tool
 * 分析矿池整体算力变化，定位算力变化最大的用户
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PoolFluctuationTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "分析矿池整体算力波动。对比今天和昨天的矿池总算力变化率，判断告警级别（>5%关注, >10%严重），并定位算力变化最大的Top10用户。可用于快速发现矿池算力异常波动的原因。")
    public String analyzePoolFluctuation(
            @ToolParam(description = "查询日期，格式YYYYMMDD，不传则为今天") Long day) {
        log.info("AI Tool: analyzePoolFluctuation, day={}", day);
        Map<String, Object> result = new HashMap<>();

        try {
            if (day == null) {
                day = Long.parseLong(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
            Long previousDay = calculatePreviousDay(day);

            // 1. 查今天和昨天的矿池算力
            StatsPoolDayBean todayPool = aiDataQueryService.getPoolHashrateByDay(day);
            StatsPoolDayBean yesterdayPool = aiDataQueryService.getPoolHashrateByDay(previousDay);

            result.put("today", day);
            result.put("yesterday", previousDay);

            if (todayPool != null) {
                result.put("todayHashrate", todayPool.getShareAccept());
                result.put("todayEarn", todayPool.getEarn());
            }
            if (yesterdayPool != null) {
                result.put("yesterdayHashrate", yesterdayPool.getShareAccept());
                result.put("yesterdayEarn", yesterdayPool.getEarn());
            }

            // 2. 计算变化率
            if (todayPool != null && yesterdayPool != null
                    && yesterdayPool.getShareAccept() != null
                    && yesterdayPool.getShareAccept().compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal changeRate = todayPool.getShareAccept().subtract(yesterdayPool.getShareAccept())
                        .divide(yesterdayPool.getShareAccept(), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
                result.put("changeRate", changeRate.doubleValue());

                // 告警级别
                double absRate = Math.abs(changeRate.doubleValue());
                String alertLevel = absRate > 10 ? "严重" : absRate > 5 ? "关注" : "正常";
                result.put("alertLevel", alertLevel);
            }

            // 3. 查矿池报告（包含各币种数据）
            List<PoolReportMiningPoolBean> reports = aiDataQueryService.getPoolReportByDay(day);
            if (reports != null && !reports.isEmpty()) {
                result.put("poolReports", reports.stream().map(r -> Map.of(
                        "coinName", r.getCoinName() != null ? r.getCoinName() : "unknown",
                        "poolHashrate", r.getPoolHashrate() != null ? r.getPoolHashrate() : BigDecimal.ZERO,
                        "allHashrate", r.getAllHashrate() != null ? r.getAllHashrate() : BigDecimal.ZERO,
                        "userNumber", r.getUserNumber() != null ? r.getUserNumber() : 0,
                        "output", r.getOutput() != null ? r.getOutput() : 0,
                        "outputIncome", r.getOutputIncome() != null ? r.getOutputIncome() : BigDecimal.ZERO,
                        "profit", r.getProfit() != null ? r.getProfit() : BigDecimal.ZERO
                )).collect(java.util.stream.Collectors.toList()));
            }

            // 4. 查算力变化最大的Top10用户
            List<Map<String, Object>> topUsers = aiDataQueryService.getTopHashrateChangeUsers(day, previousDay, 10);
            result.put("topHashrateDecreaseUsers", topUsers);

            result.put("success", true);
        } catch (Exception e) {
            log.error("analyzePoolFluctuation error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    private Long calculatePreviousDay(Long day) {
        int year = (int) (day / 10000);
        int month = (int) ((day % 10000) / 100);
        int d = (int) (day % 100);
        java.time.LocalDate date = java.time.LocalDate.of(year, month, d).minusDays(1);
        return Long.parseLong(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
