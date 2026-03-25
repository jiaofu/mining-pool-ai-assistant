package com.binance.pool.controller;


import com.alibaba.fastjson.JSON;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.interceptor.AccessAuth;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.pool.PoolSavingsService;
import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.savings.PendingAmountDetailRet;
import com.binance.pool.service.vo.savings.ReportSavingInterestRet;
import com.binance.pool.service.vo.savings.UserIdSavingsArg;
import com.binance.pool.service.vo.savings.UserSavingsDetailArg;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Tag(name = "矿池宝接口", description = "矿池宝接口")
@RequestMapping("/mining-api/v1/private/pool/savings")
@RestController
public class PoolSavingsController {

    @Resource
    PoolSavingsService poolSavingsService;

    @Operation(summary ="持仓统计(持仓+利息)")
    @GetMapping("pendingAmountStatistics")
    @AccessAuth
    @ResponseBody
    public ResultBean<BigDecimal> pendingAmountStatistics(@ParameterObject @Validated UserIdSavingsArg arg, HttpServletRequest request) {
        long uidPoolBinance = Long.parseLong(request.getAttribute(Constants.UID_POOL_BINANCE).toString());

        ResultBean resultBean = poolSavingsService.pendingAmountStatistics(uidPoolBinance);
        log.info(" pendingAmountStatistics 持仓统计(持仓+利息)"+ JSON.toJSONString(resultBean));
        return resultBean;
    }

    @Operation(summary ="持仓明细")
    @GetMapping("pendingAmountDetail")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PendingAmountDetailRet>> pendingAmountDetail(@ParameterObject @Validated UserSavingsDetailArg arg, HttpServletRequest request) {
        long uidPoolBinance = Long.parseLong(request.getAttribute(Constants.UID_POOL_BINANCE).toString());
        Long coinId = null;
        if(StringUtils.isNotEmpty(arg.getAsset())){
            PoolCoinBean poolCoinBean = PoolCoinEnum.INSTANCE.getMapStr().get(arg.getAsset().toUpperCase());
            if(poolCoinBean == null){
                return ResultBean.error(ReturnCodeEnum.COIN_ERROR);
            }
            coinId = poolCoinBean.getId();
        }

        ResultBean resultBean =  poolSavingsService.pendingAmountDetail(uidPoolBinance,coinId);
       // log.info(" pendingAmountDetail 持仓明细 "+ JSON.toJSONString(resultBean));
        return resultBean;
    }

    @Operation(summary ="收益")
    @GetMapping("income")
    @AccessAuth
    @ResponseBody
    public ResultBean<ReportSavingInterestRet> income(@ParameterObject @Validated UserIdSavingsArg arg, HttpServletRequest request) {
       // log.info(" income 持仓明细 "+ JSON.toJSONString(arg));
        long uidPoolBinance = Long.parseLong(request.getAttribute(Constants.UID_POOL_BINANCE).toString());
        ResultBean resultBean = poolSavingsService.income(uidPoolBinance);
        //log.info(" income 持仓明细 "+ JSON.toJSONString(resultBean));
        return resultBean;
    }

}
