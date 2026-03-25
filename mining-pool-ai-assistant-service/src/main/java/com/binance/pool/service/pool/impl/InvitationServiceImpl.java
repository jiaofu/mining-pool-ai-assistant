package com.binance.pool.service.pool.impl;

import com.alibaba.fastjson.JSON;
import com.binance.pool.base.bean.PoolUserInvitationRecordTempBean;
import com.binance.pool.base.bean.PoolUserInvitationRewardBean;
import com.binance.pool.base.bean.PoolUserInvitationUpdateBean;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.pool.PoolUserInvitationRecordTempDao;
import com.binance.pool.dao.pool.PoolUserInvitationRewardDao;
import com.binance.pool.dao.pool.PoolUserInvitationUpdateDao;
import com.binance.pool.service.cache.CoinCacheService;
import com.binance.pool.service.cache.InvitationCacheService;
import com.binance.pool.service.cache.UserCacheServer;
import com.binance.pool.service.enums.HashRateHumanEnum;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.pool.InvitationService;
import com.binance.pool.service.util.DateUtil;
import com.binance.pool.service.util.ExportExcelUtil;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.invitation.*;
import com.binance.pool.service.vo.miner.HumanRateVo;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    @Resource
    PoolUserInvitationUpdateDao poolUserRemoveInvitationDao;

    @Resource
    PoolUserInvitationRecordTempDao poolUserInvitationRecordTempDao;

    @Resource
    PoolUserInvitationRewardDao poolUserInvitationRewardDao;


    @Autowired
    CoinCacheService coinCacheService;

    @Resource
    UserCacheServer userCacheServer;

    @Resource
    InvitationCacheService invitationCacheService;


    private static final long sixtyDay = 60 * 24 * 60 * 60 * 1000l;
    @Override
    public ResultBean insertPoolUserRemoveInvitation(RemoveInvitationArg arg) {

        if(arg.getRequestType()==0){

            PoolUserInvitationRecordTempBean poolUserInvitationRecordTempBean = poolUserInvitationRecordTempDao.getPoolUserInvitationRecordsLast(arg.getUidBinance(),arg.getUidBinanceInvitation());
            if(poolUserInvitationRecordTempBean == null){
                log.info(" insertPoolUserRemoveInvitation uidBinance:{}  uidBinanceInvitation:{} type:{} 不存在数据库中，直接返回",arg.getUidBinance(),arg.getUidBinanceInvitation(),arg.getRequestType());
                PoolUserRemoveInvitationRet invitationRet = PoolUserRemoveInvitationRet.builder()
                        .uidBinanceInvitation(arg.getUidBinanceInvitation())
                        .requestType(arg.getRequestType())
                        .uidBinance(arg.getUidBinance())
                        .build();

                return ResultBean.ok(invitationRet);
            }
        }


        PoolUserInvitationUpdateBean bean = PoolUserInvitationUpdateBean.builder()
                        .uidBinanceInvitation(arg.getUidBinanceInvitation())
                                .status(arg.getRequestType())
                                        .uidBinance(arg.getUidBinance())
                                                .build();

        Integer result =  poolUserRemoveInvitationDao.saveRemoveInvitation(bean);


        log.info(" insertPoolUserRemoveInvitation 插入的数据结果:{} 参数:{} ",result, JSON.toJSONString(arg));
        if(result>0){
            PoolUserRemoveInvitationRet invitationRet = PoolUserRemoveInvitationRet.builder()
                    .uidBinanceInvitation(bean.getUidBinanceInvitation())
                    .requestType(arg.getRequestType())
                    .uidBinance(bean.getUidBinance())
                    .build();

            return ResultBean.ok(invitationRet);
        }
        return ResultBean.error(ReturnCodeEnum.FAIL);

    }


    @Override
    public List<InvitationReturnCommissionRet> returnCommissionDownloadJson(long uid, InvitationRecordArg arg) {
        List<InvitationReturnCommissionRet> retList = new ArrayList();
        List<PoolUserInvitationRewardBean> list = returnCommission(uid,arg);
        HashRateHumanEnum humanEnum = ExportExcelUtil.getHumanEnum(arg.getAlgoId());
        for (PoolUserInvitationRewardBean  record : list ) {
            String coinName = coinCacheService.getCoinById(record.getCoinId()).getSymbol();
            InvitationReturnCommissionRet  ret = new InvitationReturnCommissionRet();
            ret.setDate(record.getDay());
            ret.setInvitation_user_id(record.getUidPoolBinanceInvitation());
            HumanRateVo vo = HashRateHumanEnum.getHumanRateUnit(record.getDayHashRate(), humanEnum);
            ret.setDay_hash(vo.getHash());
            ret.setUnit(vo.getUnit());
            ret.setMiner_profit(record.getMiningReward().setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + coinName);
            ret.setReward_rate(record.getRewardRate().multiply(ExportExcelUtil.ONE_HUNDRED).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "%");
            ret.setReward_amount(record.getReward().setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + coinName);
            retList.add(ret);
        }

        return retList;
    }

    @Override
    public List<InvitationReturnCashRet> returnCashJson(long uid, InvitationRecordArg arg) {
        Long startDate = DateUtil.getDayByTime(arg.getStartDate());
        Long endDate = DateUtil.getDayByTime(arg.getEndDate());
        if (endDate == null) {
            endDate = DateUtil.getMinerDayBefore(0);
        }
        if (startDate == null) {
            startDate = DateUtil.getBeforeDay(endDate, 61);
        }
        HashRateHumanEnum humanEnum = ExportExcelUtil.getHumanEnum(arg.getAlgoId());
        List<PoolUserInvitationRewardBean> list = poolUserInvitationRewardDao.getPoolUserInvitationRewardInvitationsBeans(uid, arg.getAlgoId(), 1, startDate, endDate);
        List<InvitationReturnCashRet> retList = new ArrayList();
        for (PoolUserInvitationRewardBean  record : list ) {
            String coinName = coinCacheService.getCoinById(record.getCoinId()).getSymbol();
            InvitationReturnCashRet  ret = new InvitationReturnCashRet();
            ret.setDate(record.getDay());

            HumanRateVo vo = HashRateHumanEnum.getHumanRateUnit(record.getDayHashRate(), humanEnum);
            ret.setDay_hash(vo.getHash());
            ret.setUnit(vo.getUnit());
            ret.setMiner_profit(record.getMiningReward().setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + coinName);
            ret.setCash_rate(record.getRewardRate().multiply(ExportExcelUtil.ONE_HUNDRED).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "%");
            ret.setCash_amount(record.getReward().setScale(8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + coinName);
            retList.add(ret);
        }

        return retList;
    }

    @Override
    public  List<InvitationRecordRet> recordJson(long uid, InvitationRecordArg arg) {


        Long startDate = arg.getStartDate();
        Long endDate = arg.getEndDate();
        if (endDate == null) {
            endDate = System.currentTimeMillis();
        }
        if (startDate == null) {
            startDate = endDate - sixtyDay;
        }


        PoolBinanceUserBean poolBinanceUserBean = userCacheServer.getPoolBinanceUserBean(uid);
        List<PoolUserInvitationRecordRet> list = Lists.newArrayList();
        if (poolBinanceUserBean != null) {
            list = invitationCacheService.userInvitationRecordByTime(poolBinanceUserBean.getUidPoolBinance(), uid, arg.getAlgoId(), startDate, endDate);
        }

        HashRateHumanEnum humanEnum = ExportExcelUtil.getHumanEnum(arg.getAlgoId());

        List<InvitationRecordRet> retList = new ArrayList();
        for (PoolUserInvitationRecordRet  record : list ) {

            InvitationRecordRet  ret = new InvitationRecordRet();
            ret.setInvitation_user_id(record.getUidPoolBinanceInvitation());

            HumanRateVo humanRateVo = HashRateHumanEnum.getHumanRateUnit(record.getHashRate(), humanEnum);
            ret.setHour_hash_rate(humanRateVo.getHash());
            ret.setUnit(humanRateVo.getUnit());
            ret.setHash_wave_rate((record.getHashWave() == null ? 0 : (int) (record.getHashWave() * 10000)) / 100d + "%");
            ret.setReward_rate(record.getReturnCommissionRate() == null ? "0" : record.getReturnCommissionRate().multiply(ExportExcelUtil.ONE_HUNDRED).setScale(6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString() + "%");
            ret.setTotal_amount(ExportExcelUtil.getTotalReword(record));
            retList.add(ret);
        }

        return retList;

    }

    /**
     * 获取邀请数据
     * @param uid
     * @param arg
     * @return
     */
    private List<PoolUserInvitationRewardBean> returnCommission(long uid, InvitationRecordArg arg){
        Long startDate = DateUtil.getDayByTime(arg.getStartDate());
        Long endDate = DateUtil.getDayByTime(arg.getEndDate());
        if (endDate == null) {
            endDate = DateUtil.getMinerDayBefore(0);
        }
        if (startDate == null) {
            startDate = DateUtil.getBeforeDay(endDate, 61);
        }
        // 支持任意天数的下载
//        Long earlyStart = DateUtil.getBeforeDay(endDate, 61);
//        if (startDate < earlyStart) {
//            startDate = earlyStart;
//        }
        List<PoolUserInvitationRewardBean> list = poolUserInvitationRewardDao.getPoolUserInvitationRewardBeans(uid, arg.getAlgoId(), 0, startDate, endDate);
        return list;
    }
}
