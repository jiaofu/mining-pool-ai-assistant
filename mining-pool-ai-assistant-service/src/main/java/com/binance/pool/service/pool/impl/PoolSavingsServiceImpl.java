package com.binance.pool.service.pool.impl;

import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.bean.PoolSavingsAssets;
import com.binance.pool.dao.bean.PoolSavingsProduct;
import com.binance.pool.dao.bean.PoolSavingsRequest;
import com.binance.pool.dao.pool.PoolSavingsRequestDao;
import com.binance.pool.service.cache.UserSavingCacheServer;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.pool.PoolSavingsProductService;
import com.binance.pool.service.pool.PoolSavingsRequestService;
import com.binance.pool.service.pool.PoolSavingsService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.savings.PendingAmountDetailRet;
import com.binance.pool.service.vo.savings.ReportSavingInterestRet;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PoolSavingsServiceImpl implements PoolSavingsService {

    @Resource
    UserSavingCacheServer userSavingCacheServer;

    @Resource
    PoolSavingsProductService poolSavingsProductService;

    @Resource
    PoolSavingsRequestDao poolSavingsRequestDao;

    @Resource
    PoolSavingsRequestService poolSavingsRequestService;


    @Override
    public ResultBean<BigDecimal> pendingAmountStatistics(Long uidPoolBinance) {

        List<PoolSavingsAssets> poolSavingsAssets = userSavingCacheServer.getPoolSavingsAssets(uidPoolBinance);

        // TODO 现在只有btc
        BigDecimal decimalAll = poolSavingsAssets.stream().filter(q -> q.getCoinId() == 1).map(t -> {
            BigDecimal decimal = BigDecimal.ZERO;
            if (t.getPendingAmount() != null) {
                decimal = decimal.add(t.getPendingAmount());
            }
            if (t.getPendingAmountInterest() != null) {
                decimal = decimal.add(t.getPendingAmountInterest());
            }
            return decimal;
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (decimalAll == null) {
            decimalAll = BigDecimal.ZERO;
        }
        return ResultBean.ok(decimalAll);
    }

    @Override
    public ResultBean<List<PendingAmountDetailRet>> pendingAmountDetail(Long uidPoolBinance, Long coinId) {
        List<PoolSavingsAssets> poolSavingsAssets = userSavingCacheServer.getPoolSavingsAssets(uidPoolBinance);
        Map<Long, List<PoolSavingsAssets>> listMap = null;
        if (coinId != null) {
            listMap = poolSavingsAssets.stream().filter(q -> q.getCoinId() == coinId).collect(Collectors.groupingBy(PoolSavingsAssets::getCoinId));
        }else {
            listMap = poolSavingsAssets.stream().collect(Collectors.groupingBy(PoolSavingsAssets::getCoinId));
        }

        List<PendingAmountDetailRet> result = new ArrayList<>();
        for (Map.Entry<Long, List<PoolSavingsAssets>> entity : listMap.entrySet()) {


            PoolCoinBean poolCoinBean = PoolCoinEnum.INSTANCE.getMap().get(entity.getKey());
            BigDecimal pendingAmount = BigDecimal.ZERO;
            BigDecimal pendingAmountInterest = BigDecimal.ZERO;
            BigDecimal totalInterestAmount = BigDecimal.ZERO;
            BigDecimal annualizedRate = null;
            Long poolSavingTime = null;
            for (PoolSavingsAssets savingsAssets : entity.getValue()) {
                if (savingsAssets.getPendingAmount() != null) {
                    pendingAmount = pendingAmount.add(savingsAssets.getPendingAmount());
                }
                if (savingsAssets.getPendingAmountInterest() != null) {
                    pendingAmountInterest = pendingAmountInterest.add(savingsAssets.getPendingAmountInterest());
                }
                if (savingsAssets.getTotalInterestAmount() != null) {
                    totalInterestAmount = totalInterestAmount.add(savingsAssets.getTotalInterestAmount());

                }
                //TODO 多个矿池宝的产品会出问题
                if(annualizedRate == null){
                    PoolSavingsProduct product= poolSavingsProductService.getPoolSavingsProductByIdCache(savingsAssets.getPoolSavingsId());
                    annualizedRate = product.getAnnualizedRate();
                }
                PoolSavingsRequest poolSavingsRequest = poolSavingsRequestService.getPoolSavingsRequestByPoolUid(uidPoolBinance,savingsAssets.getPoolSavingsId(),0);

                if(poolSavingsRequest != null && poolSavingsRequest.getDay() != null){
                    poolSavingTime =poolSavingsRequest.getCreatedDate().getTime();
                }
            }
            PendingAmountDetailRet detailRet = PendingAmountDetailRet.builder().asset(poolCoinBean.getSymbol())
                    .annualizedRate(annualizedRate).pendingAmount(pendingAmount).pendingAmountInterest(pendingAmountInterest).
                    totalInterestAmount(totalInterestAmount).poolSavingTime(poolSavingTime).build();
            result.add(detailRet);
        }
        return ResultBean.ok(result);

    }

    @Override
    public ResultBean<ReportSavingInterestRet> income(Long uidPoolBinance) {
        PoolReportSavingsInterestBean bean = userSavingCacheServer.getPoolReportSavingsInterests(uidPoolBinance);
        ReportSavingInterestRet ret = ReportSavingInterestRet.builder().day(bean.getDay())
                .monthAmount(bean.getMonthAmount()).seasonAmount(bean.getSeasonAmount())
                .dayThirtyAmount(bean.getDayThirtyAmount())
                .yearAmount(bean.getYearAmount()).yesterdayAmount(bean.getYesterdayAmount())
                .totalAmount(bean.getTotalAmount())
                .build();
        return ResultBean.ok(ret);
    }
}
