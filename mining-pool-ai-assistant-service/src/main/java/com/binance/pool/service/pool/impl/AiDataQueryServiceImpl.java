package com.binance.pool.service.pool.impl;

import com.binance.pool.base.bean.PoolBillingLogBean;
import com.binance.pool.base.bean.PoolPriceHashrateBean;
import com.binance.pool.base.mapper.PoolBillingLogMapper;
import com.binance.pool.base.mapper.PoolPriceHashrateMapper;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.bean.PoolUserBillBean;
import com.binance.pool.dao.bean.coinstats.StatsPoolDayBean;
import com.binance.pool.dao.bean.coinstats.StatsUsersDayBean;
import com.binance.pool.dao.mapper.PoolUserBillMapper;
import com.binance.pool.dao.mapper.pool.PoolReportMiningPoolMapper;
import com.binance.pool.dao.mapper.pool.PoolReportSavingsInterestMapper;
import com.binance.pool.dao.mapper.pool.PoolSavingsAssetsMapper;
import com.binance.pool.dao.mapper.pool.PoolSavingsProductMapper;
import com.binance.pool.dao.mapper.pool.StatsPoolDayMapper;
import com.binance.pool.dao.mapper.pool.StatsUsersDayMapper;
import com.binance.pool.dao.mapper.pool.UserMapper;
import com.binance.pool.service.pool.AiDataQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiDataQueryServiceImpl implements AiDataQueryService {

    private final UserMapper userMapper;
    private final PoolUserBillMapper poolUserBillMapper;
    // 来自 mining-base
    private final PoolBillingLogMapper poolBillingLogMapper;
    private final PoolPriceHashrateMapper poolPriceHashrateMapper;

    private final StatsUsersDayMapper statsUsersDayMapper;
    private final StatsPoolDayMapper statsPoolDayMapper;
    private final PoolReportMiningPoolMapper poolReportMiningPoolMapper;
    private final PoolSavingsAssetsMapper poolSavingsAssetsMapper;
    private final PoolSavingsProductMapper poolSavingsProductMapper;
    private final PoolReportSavingsInterestMapper poolReportSavingsInterestMapper;

    // ========== 用户相关 ==========

    @Override
    public PoolUserBean getUserByPoolUsername(String poolUsername) {
        return userMapper.getUsersByPoolName(poolUsername);
    }

    @Override
    public PoolUserBean getUserByPuid(Long puid) {
        Example example = new Example(PoolUserBean.class);
        example.createCriteria().andEqualTo("id", puid);
        List<PoolUserBean> list = userMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    // ========== 账单相关 ==========

    @Override
    public List<PoolBillingLogBean> getBillingLogByDay(Long day) {
        Example example = new Example(PoolBillingLogBean.class);
        example.createCriteria().andEqualTo("day", day);
        example.setOrderByClause("algo_id ASC");
        return poolBillingLogMapper.selectByExample(example);
    }

    @Override
    public PoolBillingLogBean getBillingLogByDayAndAlgo(Long day, Long algoId) {
        Example example = new Example(PoolBillingLogBean.class);
        example.createCriteria().andEqualTo("day", day).andEqualTo("algoId", algoId);
        List<PoolBillingLogBean> list = poolBillingLogMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PoolBillingLogBean> getBillingLogByDayRange(Long startDay, Long endDay) {
        Example example = new Example(PoolBillingLogBean.class);
        example.createCriteria()
                .andGreaterThanOrEqualTo("day", startDay)
                .andLessThanOrEqualTo("day", endDay);
        example.setOrderByClause("day ASC, algo_id ASC");
        return poolBillingLogMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, Object>> getBillCountByDay(Long day) {
        Example example = new Example(PoolUserBillBean.class);
        example.createCriteria().andEqualTo("day", day);
        List<PoolUserBillBean> bills = poolUserBillMapper.selectByExample(example);

        return bills.stream()
                .collect(Collectors.groupingBy(PoolUserBillBean::getAlgoId, Collectors.counting()))
                .entrySet().stream()
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("algoId", e.getKey());
                    map.put("count", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PoolUserBillBean> getUserBillsByRange(Long puid, Long coinId, Long startDay, Long endDay) {
        Example example = new Example(PoolUserBillBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("puid", puid)
                .andGreaterThanOrEqualTo("day", startDay)
                .andLessThanOrEqualTo("day", endDay);
        if (coinId != null) {
            criteria.andEqualTo("coinId", coinId);
        }
        example.setOrderByClause("day ASC");
        return poolUserBillMapper.selectByExample(example);
    }

    @Override
    public List<PoolUserBillBean> getUserBillsSummary(Long puid, Long startDay, Long endDay) {
        return getUserBillsByRange(puid, null, startDay, endDay);
    }

    @Override
    public List<Map<String, Object>> getTopDecreasedBillUsers(Long day, Long previousDay, int topN) {
        Example todayExample = new Example(PoolUserBillBean.class);
        todayExample.createCriteria().andEqualTo("day", day);
        List<PoolUserBillBean> todayBills = poolUserBillMapper.selectByExample(todayExample);

        Example yesterdayExample = new Example(PoolUserBillBean.class);
        yesterdayExample.createCriteria().andEqualTo("day", previousDay);
        List<PoolUserBillBean> yesterdayBills = poolUserBillMapper.selectByExample(yesterdayExample);

        Map<Long, Long> todayCountMap = todayBills.stream()
                .collect(Collectors.groupingBy(PoolUserBillBean::getPuid, Collectors.counting()));
        Map<Long, Long> yesterdayCountMap = yesterdayBills.stream()
                .collect(Collectors.groupingBy(PoolUserBillBean::getPuid, Collectors.counting()));

        return yesterdayCountMap.entrySet().stream()
                .map(e -> {
                    long todayCount = todayCountMap.getOrDefault(e.getKey(), 0L);
                    long diff = todayCount - e.getValue();
                    Map<String, Object> map = new HashMap<>();
                    map.put("puid", e.getKey());
                    map.put("yesterdayCount", e.getValue());
                    map.put("todayCount", todayCount);
                    map.put("diff", diff);
                    return map;
                })
                .filter(m -> (Long) m.get("diff") < 0)
                .sorted((a, b) -> Long.compare((Long) a.get("diff"), (Long) b.get("diff")))
                .limit(topN)
                .collect(Collectors.toList());
    }

    // ========== 算力相关 ==========

    @Override
    public StatsUsersDayBean getUserHashrateByDay(Long puid, Long day) {
        return statsUsersDayMapper.getUserStatsByDay(puid, day);
    }

    @Override
    public List<StatsUsersDayBean> getUserHashrateByRange(Long puid, Long startDay, Long endDay) {
        return statsUsersDayMapper.getUserStatsByDayRange(puid, startDay, endDay);
    }

    @Override
    public StatsPoolDayBean getPoolHashrateByDay(Long day) {
        return statsPoolDayMapper.getPoolStatsByDay(day);
    }

    @Override
    public List<StatsPoolDayBean> getPoolHashrateByRange(Long startDay, Long endDay) {
        return statsPoolDayMapper.getPoolStatsByDayRange(startDay, endDay);
    }

    @Override
    public List<Map<String, Object>> getTopHashrateChangeUsers(Long today, Long yesterday, int topN) {
        List<StatsUsersDayBean> todayStats = statsUsersDayMapper.getUserStatsByDayRange(null, today, today);
        List<StatsUsersDayBean> yesterdayStats = statsUsersDayMapper.getUserStatsByDayRange(null, yesterday, yesterday);

        if (todayStats == null || yesterdayStats == null) {
            return Collections.emptyList();
        }

        Map<Long, StatsUsersDayBean> todayMap = todayStats.stream()
                .collect(Collectors.toMap(StatsUsersDayBean::getPuid, s -> s, (a, b) -> a));

        return yesterdayStats.stream()
                .map(y -> {
                    StatsUsersDayBean t = todayMap.get(y.getPuid());
                    BigDecimal todayHashrate = t != null ? t.getShareAccept() : BigDecimal.ZERO;
                    BigDecimal diff = todayHashrate.subtract(y.getShareAccept());
                    Map<String, Object> map = new HashMap<>();
                    map.put("puid", y.getPuid());
                    map.put("yesterdayHashrate", y.getShareAccept());
                    map.put("todayHashrate", todayHashrate);
                    map.put("diff", diff);
                    map.put("changeRate", y.getShareAccept().compareTo(BigDecimal.ZERO) != 0
                            ? diff.divide(y.getShareAccept(), 4, RoundingMode.HALF_UP).doubleValue() * 100
                            : 0.0);
                    return map;
                })
                .sorted((a, b) -> ((BigDecimal) a.get("diff")).compareTo((BigDecimal) b.get("diff")))
                .limit(topN)
                .collect(Collectors.toList());
    }

    // ========== 矿池报告 ==========

    @Override
    public List<PoolReportMiningPoolBean> getPoolReportByDay(Long day) {
        Example example = new Example(PoolReportMiningPoolBean.class);
        example.createCriteria().andEqualTo("day", day);
        return poolReportMiningPoolMapper.selectByExample(example);
    }

    // ========== 价格收益 ==========

    @Override
    public PoolPriceHashrateBean getLatestCoinPrice(Long coinId) {
        Example example = new Example(PoolPriceHashrateBean.class);
        example.createCriteria().andEqualTo("type", 0L).andEqualTo("coinId", coinId);
        example.setOrderByClause("time DESC");
        List<PoolPriceHashrateBean> list = poolPriceHashrateMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PoolPriceHashrateBean> getLatestAllCoinPrice() {
        // 先查最大 time
        Example maxExample = new Example(PoolPriceHashrateBean.class);
        maxExample.createCriteria().andEqualTo("type", 0L);
        maxExample.setOrderByClause("time DESC");
        List<PoolPriceHashrateBean> latest = poolPriceHashrateMapper.selectByExample(maxExample);
        if (latest.isEmpty()) {
            return Collections.emptyList();
        }
        Long maxTime = latest.get(0).getTime();

        Example example = new Example(PoolPriceHashrateBean.class);
        example.createCriteria().andEqualTo("type", 0L).andEqualTo("time", maxTime);
        return poolPriceHashrateMapper.selectByExample(example);
    }

    @Override
    public List<PoolPriceHashrateBean> getCoinPriceByRange(Long coinId, Long startDay, Long endDay) {
        Example example = new Example(PoolPriceHashrateBean.class);
        example.createCriteria()
                .andEqualTo("type", 0L)
                .andEqualTo("coinId", coinId)
                .andGreaterThanOrEqualTo("time", startDay)
                .andLessThanOrEqualTo("time", endDay);
        example.setOrderByClause("time ASC");
        return poolPriceHashrateMapper.selectByExample(example);
    }

    // ========== 矿池宝 ==========

    @Override
    public List<PoolSavingsAssets> getUserSavingsAssets(Long uidPoolBinance) {
        Example example = new Example(PoolSavingsAssets.class);
        example.createCriteria().andEqualTo("uidPoolBinance", uidPoolBinance);
        return poolSavingsAssetsMapper.selectByExample(example);
    }

    @Override
    public PoolReportSavingsInterestBean getUserSavingsInterest(Long uidPoolBinance) {
        Example example = new Example(PoolReportSavingsInterestBean.class);
        example.createCriteria().andEqualTo("uidPoolBinance", uidPoolBinance);
        example.setOrderByClause("day DESC");
        List<PoolReportSavingsInterestBean> list = poolReportSavingsInterestMapper.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<PoolSavingsProduct> getActiveSavingsProducts() {
        Example example = new Example(PoolSavingsProduct.class);
        example.createCriteria().andEqualTo("status", 0);
        return poolSavingsProductMapper.selectByExample(example);
    }
}
