package com.binance.pool.controller;


import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;
import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;
import com.binance.pool.interceptor.AccessAuth;
import com.binance.pool.service.pool.PoolReportService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.report.DayArg;
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
@Tag(name = "财务接口某一天", description = "财务数据某一天")
@RequestMapping("/mining-api/v1/private/pool/finance")
@RestController
public class PoolReportOneController {



    @Resource
    PoolReportService poolReportService;
    /**
     * 总表粉色部分
     * @param arg
     * @param request
     * @return
     */

    @Operation(summary ="总表数据统计")
    @GetMapping("summaryOne")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolBean>> summaryOne(@ParameterObject @Validated DayArg arg, HttpServletRequest request){
       return poolReportService.summaryOne(arg);
    }

    @Operation(summary ="币安自研部分数据统计")
    @GetMapping("selfOne")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolSelfBean>> selfOne(@ParameterObject @Validated DayArg arg, HttpServletRequest request){
        return poolReportService.selfOne(arg);
    }

    @Operation(summary ="合作伙伴的数据统计")
    @GetMapping("partnerOne")
    @AccessAuth
    @ResponseBody
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partnerOne(@ParameterObject @Validated DayArg arg, HttpServletRequest request){
        return poolReportService.partnerOne(arg);
    }

    @Operation(summary ="机枪池的统计")
    @GetMapping("gunOne")
    @AccessAuth
    @ResponseBody
    public ResultBean<PoolReportMiningPoolGunBean> gunOne(@ParameterObject @Validated DayArg arg, HttpServletRequest request){
        return poolReportService.gunOne(arg);
    }

    @Operation(summary ="累计统计")
    @GetMapping("cumulativeOne")
    @AccessAuth
    @ResponseBody
    public ResultBean<PoolReportMiningPoolCumulativeBean> cumulativeOne(@ParameterObject @Validated DayArg arg, HttpServletRequest request){
        return poolReportService.cumulativeOne(arg);
    }
}
