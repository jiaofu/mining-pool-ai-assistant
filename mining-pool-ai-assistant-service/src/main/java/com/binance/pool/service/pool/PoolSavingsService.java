package com.binance.pool.service.pool;

import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.savings.PendingAmountDetailRet;
import com.binance.pool.service.vo.savings.ReportSavingInterestRet;
import com.binance.pool.service.vo.savings.UserIdSavingsArg;
import com.binance.pool.service.vo.savings.UserSavingsDetailArg;

import java.math.BigDecimal;
import java.util.List;

public interface PoolSavingsService {


    /**
     * 资产统计
     *
     * @param
     * @return
     */
    ResultBean<BigDecimal> pendingAmountStatistics(Long uidPoolBinance);

    /**
     * 持仓明细
     *
     * @param
     * @return
     */
    ResultBean<List<PendingAmountDetailRet>> pendingAmountDetail(Long uidPoolBinance, Long coinId);

    /**
     * 收入
     *
     * @param
     * @return
     */
    ResultBean<ReportSavingInterestRet> income(Long uidPoolBinance);
}
