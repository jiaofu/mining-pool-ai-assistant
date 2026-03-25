package com.binance.pool.service.ai.tools;

import com.alibaba.fastjson.JSON;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.PoolUserBillBean;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * F-5: 用户账单明细查询 Tool
 * 查询用户某段时间的账单明细
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserBillingTool {

    private final AiDataQueryService aiDataQueryService;

    @Tool(description = "查询矿池用户的账单明细，包括每日收益(earn)、到手金额(userPayment)、服务费(serviceChargeFee)、矿工手续费(minerNetworkFee)、有效/过期/拒绝share数等。可以按币种筛选。适合用户质疑收益时逐条分析。")
    public String queryUserBilling(
            @ToolParam(description = "矿池用户名，如 pool_user_888") String poolUsername,
            @ToolParam(description = "币种ID，如BTC=1，ETH=2，LTC=4。不传则查全部币种") Long coinId,
            @ToolParam(description = "开始日期，格式YYYYMMDD") Long startDay,
            @ToolParam(description = "结束日期，格式YYYYMMDD") Long endDay) {
        log.info("AI Tool: queryUserBilling, poolUsername={}, coinId={}, startDay={}, endDay={}", poolUsername, coinId, startDay, endDay);
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
            result.put("startDay", startDay);
            result.put("endDay", endDay);

            // 2. 查账单明细
            List<PoolUserBillBean> bills = aiDataQueryService.getUserBillsByRange(user.getId(), coinId, startDay, endDay);

            if (bills == null || bills.isEmpty()) {
                result.put("success", true);
                result.put("message", "该时间范围内无账单数据");
                return JSON.toJSONString(result);
            }

            // 3. 转换为明细列表
            List<Map<String, Object>> billDetails = new ArrayList<>();
            BigDecimal totalEarn = BigDecimal.ZERO;
            BigDecimal totalPayment = BigDecimal.ZERO;
            BigDecimal totalServiceFee = BigDecimal.ZERO;
            BigDecimal totalMinerFee = BigDecimal.ZERO;

            for (PoolUserBillBean bill : bills) {
                Map<String, Object> detail = new HashMap<>();
                detail.put("day", bill.getDay());
                detail.put("coinId", bill.getCoinId());
                detail.put("algoId", bill.getAlgoId());
                detail.put("type", bill.getType());
                detail.put("earn", bill.getEarn());
                detail.put("userPayment", bill.getUserPayment());
                detail.put("serviceChargeFee", bill.getServiceChargeFee());
                detail.put("minerNetworkFee", bill.getMinerNetworkFee());
                detail.put("shareAccept", bill.getShareAccept());
                detail.put("shareStale", bill.getShareStale());
                detail.put("shareReject", bill.getShareReject());
                detail.put("dayHashRate", bill.getDayHashRate());
                detail.put("status", bill.getStatus());
                billDetails.add(detail);

                if (bill.getEarn() != null) totalEarn = totalEarn.add(bill.getEarn());
                if (bill.getUserPayment() != null) totalPayment = totalPayment.add(bill.getUserPayment());
                if (bill.getServiceChargeFee() != null) totalServiceFee = totalServiceFee.add(bill.getServiceChargeFee());
                if (bill.getMinerNetworkFee() != null) totalMinerFee = totalMinerFee.add(bill.getMinerNetworkFee());
            }

            result.put("billDetails", billDetails);

            // 4. 汇总统计
            BigDecimal avgDailyEarn = totalEarn.divide(BigDecimal.valueOf(bills.size()), 8, RoundingMode.HALF_UP);
            result.put("summary", Map.of(
                    "totalBills", bills.size(),
                    "totalEarn", totalEarn,
                    "totalUserPayment", totalPayment,
                    "totalServiceChargeFee", totalServiceFee,
                    "totalMinerNetworkFee", totalMinerFee,
                    "avgDailyEarn", avgDailyEarn,
                    "effectiveFeeRate", totalEarn.compareTo(BigDecimal.ZERO) != 0
                            ? totalServiceFee.divide(totalEarn, 4, RoundingMode.HALF_UP).doubleValue() * 100 + "%" : "N/A"
            ));

            // 5. 状态说明
            result.put("statusMapping", Map.of(
                    0, "待支付",
                    1, "支付中",
                    2, "已支付"
            ));
            result.put("typeMapping", Map.of(
                    0, "挖矿",
                    1, "联合挖矿",
                    2, "活动奖励",
                    4, "机枪池"
            ));

            result.put("success", true);
        } catch (Exception e) {
            log.error("queryUserBilling error", e);
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return JSON.toJSONString(result);
    }
}
