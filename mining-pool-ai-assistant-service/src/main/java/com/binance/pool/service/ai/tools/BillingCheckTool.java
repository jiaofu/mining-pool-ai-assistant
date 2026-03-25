package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.base.bean.PoolBillingLogBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-1: 账单检查与分析 Tool
 * 检查某天的出账状态，判断是否正常
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BillingCheckTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "检查某天的矿池出账状态。查询各算法的出账流程状态（0:正在生成中间账单, 1:已生成中间账单, 2:正在确认账单, 3:已生成最终账单可打款），同时统计当日与前一日的账单数量对比，检测是否有异常。参数day格式为YYYYMMDD，如20250320。")
    public String checkDailyBilling(@ToolParam(description = "账单日期，格式YYYYMMDD，如20250320") Long day) {
        log.info("AI Tool: checkDailyBilling, day={}", day);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 查出账状态
            List<PoolBillingLogBean> billingLogs = aiDataQueryService.getBillingLogByDay(day);
            result.put("billingStatus", billingLogs);

            // 2. 查当日账单数量
            List<Map<String, Object>> todayBillCount = aiDataQueryService.getBillCountByDay(day);
            result.put("todayBillCount", todayBillCount);

            // 3. 查前一天账单数量对比
            Long previousDay = calculatePreviousDay(day);
            List<Map<String, Object>> yesterdayBillCount = aiDataQueryService.getBillCountByDay(previousDay);
            result.put("previousDay", previousDay);
            result.put("previousDayBillCount", yesterdayBillCount);

            // 4. 出账状态描述映射
            result.put("statusMapping", Map.of(
                    0, "正在生成中间账单",
                    1, "已生成中间账单",
                    2, "正在确认账单",
                    3, "已生成最终账单（可打款）"
            ));

            result.put("queryDay", day);
            result.put("success", true);
        } catch (Exception e) {
            log.error("checkDailyBilling error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    private Long calculatePreviousDay(Long day) {
        // YYYYMMDD 格式减一天
        int year = (int) (day / 10000);
        int month = (int) ((day % 10000) / 100);
        int d = (int) (day % 100);

        java.time.LocalDate date = java.time.LocalDate.of(year, month, d).minusDays(1);
        return Long.parseLong(date.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
