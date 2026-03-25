package com.binance.pool.controller;



import com.binance.pool.interceptor.AccessAuth;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.pool.InvitationService;
import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.invitation.*;
import com.binance.pool.util.JsonUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "邀请接口", description = "邀请接口")
@RequestMapping("/mining-api/v1/private/pool/invitation")
@RestController
public class InvitationController {

    @Resource
    InvitationService invitationService;

    @Operation(summary ="解绑邀请返佣")
    @PostMapping("remove")
    @AccessAuth
    @ResponseBody
    public ResultBean<PoolUserRemoveInvitationRet> invitation(@ParameterObject @Validated RemoveInvitationArg arg, HttpServletRequest request) {

        String appName = request.getParameter(Constants.APP_NAME);
        if (!appName.equalsIgnoreCase(Constants.APP_NAME_INVITATION)) {
            return ResultBean.error(ReturnCodeEnum.INTERFACE_NOT_ACCESSIBLE);
        }
        ResultBean resultBean = invitationService.insertPoolUserRemoveInvitation(arg);
        return ResultBean.ok(resultBean);
    }


    @AccessAuth
    @Operation(summary ="返佣记录Json下载")
    @GetMapping(value = "/returnCommissionJson")
    @ResponseBody
    public ResultBean< List<InvitationReturnCommissionRet>> returnCommissionJson(@ParameterObject @Validated InvitationRecordArg arg, HttpServletRequest request, HttpServletResponse response) {

        log.info(" invitationJson returnCommissionJson  uidBinance : {} arg : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg));
        String appName = request.getParameter(Constants.APP_NAME);
        if (!appName.equalsIgnoreCase(Constants.APP_NAME_INVITATION_RETURNCOMMISSION)) {
            return ResultBean.error(ReturnCodeEnum.INTERFACE_NOT_ACCESSIBLE);
        }

        if(arg.getAlgoId()-12 ==0){
            return ResultBean.ok(new ArrayList<>());
        }
        List<InvitationReturnCommissionRet> list = invitationService.returnCommissionDownloadJson(arg.getUidBinance(),arg);
        log.info(" invitationJson returnCommissionJson  uidBinance : {}  arg : {}  result : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg) ,JsonUtils.toJSONString(list));
        return ResultBean.ok(list);

    }

    @AccessAuth
    @Operation(summary ="返现记录Json下载")
    @GetMapping(value = "/returnCashJson")
    @ResponseBody
    public ResultBean<List<InvitationReturnCashRet>> returnCashJson(@ParameterObject @Validated InvitationRecordArg arg, HttpServletRequest request, HttpServletResponse response) {
        log.info(" invitationJson returnCashJson  uidBinance : {} arg : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg));
        String appName = request.getParameter(Constants.APP_NAME);
        if (!appName.equalsIgnoreCase(Constants.APP_NAME_INVITATION_RETURNCOMMISSION)) {
            return ResultBean.error(ReturnCodeEnum.INTERFACE_NOT_ACCESSIBLE);
        }

        if(arg.getAlgoId()-12 ==0){
            return ResultBean.ok(new ArrayList<>());
        }

        List<InvitationReturnCashRet> list =  invitationService.returnCashJson(arg.getUidBinance(),arg);
        log.info(" invitationJson returnCashJson  uidBinance : {}  arg : {}  result : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg) ,JsonUtils.toJSONString(list));
        return ResultBean.ok(list);

    }

    @AccessAuth
    @Operation(summary ="邀请记录Json下载")
    @GetMapping(value = "/recordJson")
    @ResponseBody
    public ResultBean<List<InvitationRecordRet>>  recordJson(@ParameterObject @Validated InvitationRecordArg arg, HttpServletRequest request, HttpServletResponse response) {

        log.info(" invitationJson recordJson  uidBinance : {}  arg : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg));
        String appName = request.getParameter(Constants.APP_NAME);
        if (!appName.equalsIgnoreCase(Constants.APP_NAME_INVITATION_RETURNCOMMISSION)) {
            return ResultBean.error(ReturnCodeEnum.INTERFACE_NOT_ACCESSIBLE);
        }

        if(arg.getAlgoId()-12 ==0){
            return ResultBean.ok(new ArrayList<>());
        }


      List<InvitationRecordRet> list = invitationService.recordJson(arg.getUidBinance(),arg);

        log.info(" invitationJson recordJson  uidBinance : {}  arg : {}  result : {}  ",arg.getUidBinance(), JsonUtils.toJSONString(arg) ,JsonUtils.toJSONString(list));
        return ResultBean.ok(list);

    }
}
