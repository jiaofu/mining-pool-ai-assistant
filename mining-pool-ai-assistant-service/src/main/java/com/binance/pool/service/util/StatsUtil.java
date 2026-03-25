package com.binance.pool.service.util;

import com.binance.pool.dao.bean.coinstats.StatsBean;
import com.binance.pool.service.enums.HashRateFactorEnum;

import java.math.BigDecimal;
import java.util.List;

public class StatsUtil {
    /**
     * 计算 算力
     *
     * @param name
     * @return
     */
    public static long getHashRate(String name, long acceptedShare, int time) {
        long factor = HashRateFactorEnum.getFactor(name);
        return  HashRateFactorEnum.hashRate(acceptedShare, factor, time);

    }

    public static BigDecimal getHashRate(String name, BigDecimal acceptedShare, int time) {
        long factor = HashRateFactorEnum.getFactor(name);
        return  HashRateFactorEnum.hashRate(acceptedShare, factor, time);

    }

    public static StatsBean addStatsBeans(List<StatsBean> statsBean) {
        StatsBean newBean = null;
        for (StatsBean other : statsBean) {
            if(newBean == null){
                newBean =other;
                continue;
            }
            newBean.setShareAccept(newBean.getShareAccept().add(other.getShareAccept())) ;

            newBean.setShareStale(newBean.getShareStale().add(other.getShareStale()));

            newBean.setShareReject(newBean.getShareReject().add(other.getShareReject()));

            newBean.setRejectDetail(newBean.getRejectDetail()+other.getRejectDetail());
            newBean.setRejectRate( HashRateUtil.getRejectRate(newBean.getShareAccept(),newBean.getShareStale(),newBean.getShareReject()) );


            if(newBean.getUpdatedAt() == null){
                newBean.setUpdatedAt(other.getUpdatedAt());
            }else {
                newBean.setUpdatedAt( newBean.getUpdatedAt().after(other.getUpdatedAt()) ? newBean.getUpdatedAt() : other.getUpdatedAt());
            }

        }
        return newBean;
    }

    public static void main(String[] args) {
      BigDecimal value =  StatsUtil.getHashRate("Bitcoin",new BigDecimal(57278464),60*60);
      System.out.println(value);
    }


}
