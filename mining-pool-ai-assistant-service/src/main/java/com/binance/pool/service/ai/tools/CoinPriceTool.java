package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.base.bean.PoolPriceHashrateBean;
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
 * F-6: 币种价格与挖矿收益查询 Tool
 * 查询币种价格和每T收益
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CoinPriceTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "查询币种的实时价格和每T挖矿收益。返回币种价格(USDT)、每T日产币量、每T日收益(USDT/CNY)、当前网络难度。支持查单个币种或全部币种，可选查历史价格走势。")
    public String queryCoinPriceAndEarnings(
            @ToolParam(description = "币种ID，如BTC=1，ETH=2，LTC=4。不传则查全部币种") Long coinId,
            @ToolParam(description = "是否查历史走势，默认false") Boolean withHistory,
            @ToolParam(description = "历史走势开始日期，格式YYYYMMDD") Long startDay,
            @ToolParam(description = "历史走势结束日期，格式YYYYMMDD") Long endDay) {
        log.info("AI Tool: queryCoinPriceAndEarnings, coinId={}", coinId);
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 查最新价格
            if (coinId != null) {
                PoolPriceHashrateBean price = aiDataQueryService.getLatestCoinPrice(coinId);
                if (price != null) {
                    result.put("latestPrice", Map.of(
                            "coinId", price.getCoinId(),
                            "price", price.getPrice(),
                            "eachHaveCoin", price.getEachHaveCoin() != null ? price.getEachHaveCoin() : BigDecimal.ZERO,
                            "eachHaveUsdt", price.getEachHaveUsdt() != null ? price.getEachHaveUsdt() : BigDecimal.ZERO,
                            "eachHaveCny", price.getEachHaveCny() != null ? price.getEachHaveCny() : BigDecimal.ZERO,
                            "difficulty", price.getDifficulty() != null ? price.getDifficulty() : BigDecimal.ZERO,
                            "time", price.getTime()
                    ));
                } else {
                    result.put("latestPrice", null);
                    result.put("message", "未找到该币种的价格数据");
                }
            } else {
                List<PoolPriceHashrateBean> allPrices = aiDataQueryService.getLatestAllCoinPrice();
                result.put("allCoinPrices", allPrices != null ? allPrices.stream().map(p -> Map.of(
                        "coinId", p.getCoinId(),
                        "price", p.getPrice(),
                        "eachHaveCoin", p.getEachHaveCoin() != null ? p.getEachHaveCoin() : BigDecimal.ZERO,
                        "eachHaveUsdt", p.getEachHaveUsdt() != null ? p.getEachHaveUsdt() : BigDecimal.ZERO,
                        "eachHaveCny", p.getEachHaveCny() != null ? p.getEachHaveCny() : BigDecimal.ZERO,
                        "difficulty", p.getDifficulty() != null ? p.getDifficulty() : BigDecimal.ZERO
                )).collect(java.util.stream.Collectors.toList()) : List.of());
            }

            // 2. 查历史走势
            if (Boolean.TRUE.equals(withHistory) && coinId != null && startDay != null && endDay != null) {
                List<PoolPriceHashrateBean> history = aiDataQueryService.getCoinPriceByRange(coinId, startDay, endDay);
                result.put("priceHistory", history != null ? history.stream().map(p -> Map.of(
                        "time", p.getTime(),
                        "price", p.getPrice(),
                        "eachHaveUsdt", p.getEachHaveUsdt() != null ? p.getEachHaveUsdt() : BigDecimal.ZERO,
                        "difficulty", p.getDifficulty() != null ? p.getDifficulty() : BigDecimal.ZERO
                )).collect(java.util.stream.Collectors.toList()) : List.of());
            }

            // 3. 币种ID映射参考
            result.put("coinIdMapping", Map.of(
                    1L, "BTC",
                    2L, "ETH",
                    4L, "LTC"
            ));

            result.put("success", true);
        } catch (Exception e) {
            log.error("queryCoinPriceAndEarnings error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
