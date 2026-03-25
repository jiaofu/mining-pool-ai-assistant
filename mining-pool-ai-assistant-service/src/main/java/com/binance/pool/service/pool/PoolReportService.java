package com.binance.pool.service.pool;

import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;
import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.report.DayArg;
import com.binance.pool.service.vo.report.DayTotalArg;

import java.util.List;

public interface PoolReportService {





    /**
     * 总表数据统计
     */
    public ResultBean<List<PoolReportMiningPoolBean>> summaryOne(DayArg arg);



    /**
     * 币安自研部分数据统计
     */
    public ResultBean<List<PoolReportMiningPoolSelfBean>> selfOne(DayArg arg);



    /**
     * 合作伙伴的数据统计
     */
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partnerOne(DayArg arg);


    /**
     * 机枪池的统计
     */
    public ResultBean<PoolReportMiningPoolGunBean> gunOne( DayArg arg);



    /**
     * 累计统计
     */
    public ResultBean<PoolReportMiningPoolCumulativeBean> cumulativeOne(DayArg arg);






    /**
     * 总表数据统计
     */
    public ResultBean<List<PoolReportMiningPoolBean>> summary(DayTotalArg arg);



    /**
     * 币安自研部分数据统计
     */
    public ResultBean<List<PoolReportMiningPoolSelfBean>> self( DayTotalArg arg);



    /**
     * 合作伙伴的数据统计
     */
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partner(DayTotalArg arg);


    /**
     * 机枪池的统计
     */
    public ResultBean<List<PoolReportMiningPoolGunBean>> gun( DayTotalArg arg);



    /**
     * 累计统计
     */
    public ResultBean<List<PoolReportMiningPoolCumulativeBean>> cumulative( DayTotalArg arg);
}
