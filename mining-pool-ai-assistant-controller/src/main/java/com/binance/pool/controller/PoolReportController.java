package com.binance.pool.controller;


import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;
import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;
import com.binance.pool.interceptor.AccessAuth;
import com.binance.pool.service.pool.PoolReportService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.report.DayTotalArg;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Tag(name = "财务接口", description = "财务数据")
@RequestMapping("/mining-api/v1/private/pool/finance")
@RestController
public class PoolReportController {


    @Resource
    PoolReportService poolReportService;


    @Operation(summary ="总表数据统计")
    @GetMapping("summary")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolBean>> summary(@ParameterObject @Validated DayTotalArg arg, HttpServletRequest request) {
        return poolReportService.summary(arg);
    }

    @Operation(summary ="币安自研部分数据统计")
    @GetMapping("self")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolSelfBean>> self(@ParameterObject @Validated DayTotalArg arg, HttpServletRequest request){
        return poolReportService.self(arg);
    }


    @Operation(summary ="合作伙伴的数据统计")
    @GetMapping("partner")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partner(@ParameterObject @Validated DayTotalArg arg, HttpServletRequest request){
        return poolReportService.partner(arg);
    }

    @Operation(summary ="机枪池的统计")
    @GetMapping("gun")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolGunBean>> gun(@ParameterObject @Validated DayTotalArg arg, HttpServletRequest request){
        return poolReportService.gun(arg);
    }

    @Operation(summary ="累计统计")
    @GetMapping("cumulative")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolCumulativeBean>> cumulative(@ParameterObject @Validated DayTotalArg arg, HttpServletRequest request){
        return poolReportService.cumulative(arg);
    }
}
