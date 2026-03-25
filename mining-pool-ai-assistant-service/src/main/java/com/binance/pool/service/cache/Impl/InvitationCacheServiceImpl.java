package com.binance.pool.service.cache.Impl;

import com.binance.pool.base.bean.PoolReportInvitationCoinBean;
import com.binance.pool.base.bean.PoolReportInvitationNumBean;
import com.binance.pool.base.bean.PoolReportInvitationRecordBean;
import com.binance.pool.base.bean.PoolReportInvitationRewardBean;
import com.binance.pool.base.bean.PoolUserInvitationRateBean;
import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolUserHashMonitor;

import com.binance.pool.dao.pool.PoolBinanceUserDao;

import com.binance.pool.dao.pool.PoolReportInvitationRecordDao;
import com.binance.pool.dao.pool.PoolUserHashMonitorDao;
import com.binance.pool.dao.pool.PoolUserInvitationRateDao;
import com.binance.pool.service.cache.InvitationCacheService;
import com.binance.pool.service.cache.UserCacheServer;
import com.binance.pool.service.enums.HashRateHumanEnum;
import com.binance.pool.service.enums.PoolCoinEnum;

import com.binance.pool.service.vo.invitation.PoolUserInvitationRecordRet;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvitationCacheServiceImpl implements InvitationCacheService {

    @Resource
    PoolReportInvitationRecordDao poolReportInvitationRecordDao;

    @Resource
    PoolBinanceUserDao poolBinanceUserDao;

    @Resource
    PoolUserInvitationRateDao poolUserInvitationRateDao;


    @Resource
    PoolUserHashMonitorDao poolUserHashMonitorDao;

    public LoadingCache<String, List<PoolUserInvitationRecordRet>> userInvitationRecordByTime = CacheBuilder.newBuilder()
            .maximumSize(100000)//设置容量上限
            .initialCapacity(100)
            .expireAfterAccess(10, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(10, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<String, List<PoolUserInvitationRecordRet>>() {
                @Override
                public List<PoolUserInvitationRecordRet> load(String key) throws Exception {

                    String[] splits = key.split("_");
                    Long uidPoolBinance = Long.parseLong(splits[0]);
                    Long uid = Long.parseLong(splits[1]);
                    Long algoId = Long.parseLong(splits[2]);
                    Long start = Long.parseLong(splits[3]);
                    Long end = Long.parseLong(splits[4]);


                    List<PoolUserInvitationRecordRet> retList = getUserRecordByTime(uidPoolBinance, uid, algoId, start, end);
                    return retList;
                }
            });


    @Override
    public List<PoolUserInvitationRecordRet> userInvitationRecordByTime(Long uidPoolBinance, Long uid, long algoId, Long start, Long end) {
//        Long uidPoolBinance = Long.parseLong(splits[0]);
//        Long uid= Long.parseLong(splits[1]);
//        Long algoId = Long.parseLong(splits[2]);
//        Long start = Long.parseLong(splits[3]);
//        Long end = Long.parseLong(splits[4]);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uidPoolBinance).append("_");
        stringBuilder.append(uid).append("_");
        stringBuilder.append(algoId).append("_");
        stringBuilder.append(start).append("_");
        stringBuilder.append(end);
        log.info(" userInvitationRecordByTime {}" , stringBuilder.toString());
        return userInvitationRecordByTime.getUnchecked(stringBuilder.toString());
    }

    private List<PoolUserInvitationRecordRet> getUserRecordByTime(Long uidPoolBinance, Long uid, long algoId, Long start, Long end) {


        List<PoolReportInvitationRecordBean> list = poolReportInvitationRecordDao.getPoolBinanceByTime(uidPoolBinance, algoId, new Date(start),  new Date(end) );
        List<PoolUserInvitationRecordRet> retList = getPoolUserInvitationRecordRet(list, uid, uidPoolBinance, algoId);
        return retList;

    }

    private List<PoolUserInvitationRecordRet> getPoolUserInvitationRecordRet(List<PoolReportInvitationRecordBean> list, Long uid, Long uidPoolBinance, long algoId) {
        List<PoolUserInvitationRecordRet> result = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return result;
        }
        List<Long> uidPoolBinanceList = list.stream().map(q -> q.getUidPoolBinanceInvitation()).collect(Collectors.toList());
        List<PoolBinanceUserBean> userBeanList = poolBinanceUserDao.getPoolBinanceUserBeans(uidPoolBinanceList);
        Map<Long, Long> poolMap = userBeanList.stream().collect(Collectors.toMap(q -> q.getUidPoolBinance(), t -> t.getUidBinance(), (o1, o2) -> o1));
        List<Long> uids = poolMap.values().stream().collect(Collectors.toList());


        Map<Long, BigDecimal> rateMap = getPoolUserInvitationRates(uid, uids, algoId);
        Map<Long, Map<String, String>> recordDetail = getUserRecordDetail(uidPoolBinance, algoId, uidPoolBinanceList);
        Map<Long, PoolUserHashMonitor> monitorMap = getPoolUserHashMonitor(uidPoolBinanceList, algoId);
        for (PoolReportInvitationRecordBean recordBean : list) {
            Long uidRecord = poolMap.get(recordBean.getUidPoolBinanceInvitation());
            PoolUserHashMonitor hashMonitor = monitorMap.get(recordBean.getUidPoolBinanceInvitation());
            BigDecimal hashRate = null;
            Double hashWave = null;
            if (hashMonitor != null) {
                hashRate = hashMonitor.getHashRate();
                hashWave = hashMonitor.getHashWave();
            }
            PoolUserInvitationRecordRet ret = PoolUserInvitationRecordRet.builder()
                    .invitationTime(recordBean.getCreatedDate().getTime())
                    .decimal(recordBean.getReward())
                    .hashRate(hashRate)
                    .uidInvitation(uidRecord)
                    .hashWave(hashWave)
                    .returnCommissionRate(rateMap.get(uidRecord))
                    .reward(recordDetail.get(recordBean.getUidPoolBinanceInvitation()))
                    .uidPoolBinanceInvitation(recordBean.getUidPoolBinanceInvitation())
                    .build();
            result.add(ret);
        }
        return result;
    }

    /**
     * 返佣比例
     *
     * @param uid
     * @param invitations
     * @param algoId
     * @return
     */
    private Map<Long, BigDecimal> getPoolUserInvitationRates(long uid, List<Long> invitations, long algoId) {
        invitations.add(0L);
        List<PoolUserInvitationRateBean> beans = poolUserInvitationRateDao.getPoolUserInvitationRates(uid, invitations, algoId);
        Map<Long, BigDecimal> map = beans.stream().collect(Collectors.toMap(q -> q.getUidBinanceInvitation(), t -> t.getReturnCommissionRate()));
        BigDecimal decimalFault = map.get(0L);
        if (decimalFault == null) {
            PoolUserInvitationRateBean beanAlgo = poolUserInvitationRateDao.getPoolUserInvitationRate(0, 0, algoId);
            decimalFault = beanAlgo.getReturnCommissionRate();
        }
        for (Long ln : invitations) {
            BigDecimal decimal = map.get(ln);
            if (decimal == null) {
                map.put(ln, decimalFault);
            }
        }
        return map;

    }

    /**
     * 获取邀请金额明细
     *
     * @param uidPoolBinance
     * @param algoId
     * @param uidPoolBinanceInvitations
     * @return
     */
    private Map<Long, Map<String, String>> getUserRecordDetail(Long uidPoolBinance, long algoId, List<Long> uidPoolBinanceInvitations) {
        Map<Long, Map<String, String>> map = new HashMap<>();
        if (uidPoolBinanceInvitations == null || uidPoolBinanceInvitations.size() == 0) {
            return map;
        }
        List<PoolReportInvitationRecordBean> poolReportInvitationRecordBeans = poolReportInvitationRecordDao.getPoolBinanceByIds(uidPoolBinance, algoId, uidPoolBinanceInvitations);

        Map<Long, List<PoolReportInvitationRecordBean>> listMap = poolReportInvitationRecordBeans.stream().collect(Collectors.groupingBy(q -> q.getUidPoolBinanceInvitation()));

        for (Map.Entry<Long, List<PoolReportInvitationRecordBean>> entity : listMap.entrySet()) {
            Map<String, String> bigDecimalMap = entity.getValue().stream().collect(Collectors.toMap(q -> PoolCoinEnum.INSTANCE.getMap().get(q.getCoinId()).getSymbol(), t -> t.getReward().toPlainString(), (o1, o2) -> o1));
            map.put(entity.getKey(), bigDecimalMap);
        }
        return map;
    }

    /**
     * 数据转化
     * <p>
     * 波动率公式
     * <p>
     * 每个子账户
     * 当 hashWave 为null 或者 hashWave = 666666
     * 设置波动率为hashWave=0
     * 或者
     * 设置  a=hashWave+1;
     * hash = hashRate/a;
     * 取数据库字段 字段 hashRate
     * <p>
     * 矿池账户
     * 波动率 = sum(所有的子账户 hash) /sum(所有的hashRate)
     *
     * @param invitationPoolLists
     * @param algoId
     * @return
     */
    private Map<Long, PoolUserHashMonitor> getPoolUserHashMonitor(List<Long> invitationPoolLists, long algoId) {

        if (invitationPoolLists == null || invitationPoolLists.size() == 0) {

            return new HashMap<>();
        }
        List<PoolUserHashMonitor> listMonitor = poolUserHashMonitorDao.getPoolUserHashMonitors(invitationPoolLists, algoId);
        Map<Long, PoolUserHashMonitor> result = new HashMap<>();
        Map<Long, List<PoolUserHashMonitor>> map = listMonitor.stream().collect(Collectors.groupingBy(PoolUserHashMonitor::getUidPoolBinance));
        for (Map.Entry<Long, List<PoolUserHashMonitor>> entry : map.entrySet()) {
            PoolUserHashMonitor poolUserHashMonitor = new PoolUserHashMonitor();


            BigDecimal dayHashRate = BigDecimal.ZERO;
            BigDecimal hashRate = BigDecimal.ZERO;
            for (PoolUserHashMonitor monitor : entry.getValue()) {
                if (monitor == null) {
                    continue;
                }
                BigDecimal dayHash = BigDecimal.ZERO;
                if (monitor.getHashWave() == null || monitor.getHashWave().doubleValue() == 666666) {
                    monitor.setHashWave(0D);
                } else {
                    BigDecimal havaWave = new BigDecimal(monitor.getHashWave() + 1);
                    if ( havaWave.compareTo(BigDecimal.ZERO) != 0) {
                        dayHash = monitor.getHashRate().divide(havaWave, 4, RoundingMode.DOWN);
                    }
                }

                dayHashRate = dayHashRate.add(dayHash);
                if (monitor.getHashRate() != null) {
                    hashRate = hashRate.add(monitor.getHashRate());
                }
            }
            BigDecimal hashWave = BigDecimal.ZERO;
            if (dayHashRate.compareTo(BigDecimal.ZERO) > 0) {
                hashWave = hashRate.subtract(dayHashRate).divide(hashRate, 4, RoundingMode.DOWN).abs();
            }
            Double doHashWate = Double.valueOf(hashWave.toPlainString());
            poolUserHashMonitor.setDayHashRate(dayHashRate);
            poolUserHashMonitor.setHashWave(doHashWate);
            poolUserHashMonitor.setHashRate(hashRate);
            result.put(entry.getKey(), poolUserHashMonitor);
        }
        return result;
    }



}
