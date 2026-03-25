package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.base.bean.PoolBillingLogBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-1(续): 批量账单异常检测 Tool
 * 批量检查多天的出账状态
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BatchBillingCheckTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "批量检查多天的矿池出账状态，识别异常天。参数为起始日期和结束日期，格式YYYYMMDD。会检查每天的出账状态和账单数量变化，标注出异常天。")
    public String batchCheckBillingAnomalies(
            @ToolParam(description = "开始日期，格式YYYYMMDD") Long startDay,
            @ToolParam(description = "结束日期，格式YYYYMMDD") Long endDay) {
        log.info("AI Tool: batchCheckBillingAnomalies, startDay={}, endDay={}", startDay, endDay);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 批量查出账日志
            List<PoolBillingLogBean> allLogs = aiDataQueryService.getBillingLogByDayRange(startDay, endDay);

            // 2. 按天分组
            Map<Long, List<PoolBillingLogBean>> logsByDay = allLogs.stream()
                    .collect(java.util.stream.Collectors.groupingBy(PoolBillingLogBean::getDay));

            // 3. 逐天分析
            List<Map<String, Object>> dailyAnalysis = new ArrayList<>();
            LocalDate start = parseDay(startDay);
            LocalDate end = parseDay(endDay);

            Long previousDayBillTotal = null;
            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                Long dayValue = Long.parseLong(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                Map<String, Object> dayResult = new HashMap<>();
                dayResult.put("day", dayValue);

                List<PoolBillingLogBean> dayLogs = logsByDay.getOrDefault(dayValue, List.of());
                dayResult.put("billingLogs", dayLogs);

                // 检查是否所有算法都完成出账
                boolean allCompleted = !dayLogs.isEmpty() && dayLogs.stream().allMatch(l -> l.getStatus() == 3);
                dayResult.put("allCompleted", allCompleted);

                // 查账单数量
                List<Map<String, Object>> billCount = aiDataQueryService.getBillCountByDay(dayValue);
                long totalBills = billCount.stream().mapToLong(m -> (Long) m.get("count")).sum();
                dayResult.put("totalBills", totalBills);

                // 与前一天对比
                if (previousDayBillTotal != null && previousDayBillTotal > 0) {
                    double changeRate = (totalBills - previousDayBillTotal) * 100.0 / previousDayBillTotal;
                    dayResult.put("billChangeRate", String.format("%.2f%%", changeRate));
                    dayResult.put("anomaly", Math.abs(changeRate) > 20); // 超过20%视为异常
                }
                previousDayBillTotal = totalBills;

                dailyAnalysis.add(dayResult);
            }

            result.put("dailyAnalysis", dailyAnalysis);
            result.put("startDay", startDay);
            result.put("endDay", endDay);
            result.put("success", true);
        } catch (Exception e) {
            log.error("batchCheckBillingAnomalies error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    private LocalDate parseDay(Long day) {
        int year = (int) (day / 10000);
        int month = (int) ((day % 10000) / 100);
        int d = (int) (day % 100);
        return LocalDate.of(year, month, d);
    }
}
