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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * F-2: 用户算力查询 Tool
 * 查询用户的实时算力、日均算力等信息
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserHashrateTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "查询矿池用户的算力信息。可以通过矿池用户名(poolUsername)查询用户当天或指定日期的算力数据，包括有效算力(shareAccept)、过期算力(shareStale)、拒绝算力(shareReject)、拒绝率(rejectRate)、收益(earn)等。")
    public String queryUserHashrate(
            @ToolParam(description = "矿池用户名，如 pool_user_888") String poolUsername,
            @ToolParam(description = "查询日期，格式YYYYMMDD。不传则查最近一天") Long day) {
        log.info("AI Tool: queryUserHashrate, poolUsername={}, day={}", poolUsername, day);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 查用户信息
            PoolUserBean user = aiDataQueryService.getUserByPoolUsername(poolUsername);
            if (user == null) {
                result.put("success", false);
                result.put("error", "未找到用户: " + poolUsername);
                return JSON.toJSONString(result);
            }

            result.put("user", Map.of(
                    "poolUsername", user.getPoolUsername(),
                    "puid", user.getId(),
                    "status", user.getStatus()
            ));

            // 2. 如果没传日期，查昨天
            if (day == null) {
                day = Long.parseLong(LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }

            // 3. 查算力数据
            StatsUsersDayBean stats = aiDataQueryService.getUserHashrateByDay(user.getId(), day);
            if (stats != null) {
                result.put("hashrate", Map.of(
                        "day", day,
                        "shareAccept", stats.getShareAccept(),
                        "shareStale", stats.getShareStale(),
                        "shareReject", stats.getShareReject(),
                        "rejectRate", stats.getRejectRate() != null ? stats.getRejectRate() : 0,
                        "earn", stats.getEarn(),
                        "score", stats.getScore()
                ));
            } else {
                result.put("hashrate", null);
                result.put("message", "该日期无算力数据");
            }

            result.put("success", true);
        } catch (Exception e) {
            log.error("queryUserHashrate error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
