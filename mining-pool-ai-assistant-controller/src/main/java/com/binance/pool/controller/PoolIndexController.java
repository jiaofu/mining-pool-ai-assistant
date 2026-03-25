package com.binance.pool.controller;


import com.binance.pool.service.cache.AlgoCacheService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.index.AlgoInfoRet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页", description = "首页接口")
@RequestMapping("/mining-api/v1/public/pool/")
@RestController
@Slf4j
public class PoolIndexController {


    @Resource
    AlgoCacheService algoCacheService;

    @Operation(summary ="算法信息")
    @GetMapping("algoList")
    @ResponseBody
    public ResultBean<List<AlgoInfoRet>> getAlgoList(HttpServletRequest request) {
        log.info(" getAlgoList list ");
        List<AlgoInfoRet> retList = algoCacheService.getAlgoInfo();
        return ResultBean.ok(retList);
    }

}
