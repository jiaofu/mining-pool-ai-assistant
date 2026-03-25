package com.binance.pool.service.pool.impl;

import com.binance.pool.base.bean.PoolReportMiningPoolCumulativeBean;
import com.binance.pool.base.bean.PoolReportMiningPoolSelfBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolGunBean;
import com.binance.pool.dao.bean.PoolReportMiningPoolPartnerBean;
import com.binance.pool.dao.pool.PoolReportMiningPoolCumulativeDao;
import com.binance.pool.dao.pool.PoolReportMiningPoolDao;
import com.binance.pool.dao.pool.PoolReportMiningPoolGunDao;
import com.binance.pool.dao.pool.PoolReportMiningPoolPartnerDao;
import com.binance.pool.dao.pool.PoolReportMiningPoolSelfDao;
import com.binance.pool.service.pool.PoolReportService;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.report.DayArg;
import com.binance.pool.service.vo.report.DayTotalArg;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class PoolReportServiceImpl implements PoolReportService {

    @Resource
    PoolReportMiningPoolSelfDao poolReportMiningPoolSelfDao;

    @Resource
    PoolReportMiningPoolPartnerDao poolReportMiningPoolPartnerDao;

    @Resource
    PoolReportMiningPoolGunDao poolReportMiningPoolGunDao;

    @Resource
    PoolReportMiningPoolDao poolReportMiningPoolDao;

    @Resource
    PoolReportMiningPoolCumulativeDao poolReportMiningPoolCumulativeDao;

    @Override
    public ResultBean<List<PoolReportMiningPoolBean>> summaryOne(DayArg arg) {
        List<PoolReportMiningPoolBean> list = poolReportMiningPoolDao.getPoolReportMiningPoolBeans(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolSelfBean>> selfOne(DayArg arg) {
        List<PoolReportMiningPoolSelfBean> list = poolReportMiningPoolSelfDao.getPoolReportMiningPoolSelfBean(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partnerOne(DayArg arg) {
        List<PoolReportMiningPoolPartnerBean> list = poolReportMiningPoolPartnerDao.getPoolReportMiningPoolPartnerBeans(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<PoolReportMiningPoolGunBean> gunOne(DayArg arg) {
        PoolReportMiningPoolGunBean poolReportMiningPoolGunBean = poolReportMiningPoolGunDao.getPoolReportMiningPoolGunBean(arg.getDay());
        return ResultBean.ok(poolReportMiningPoolGunBean);
    }

    @Override
    public ResultBean<PoolReportMiningPoolCumulativeBean> cumulativeOne(DayArg arg) {
        PoolReportMiningPoolCumulativeBean poolReportMiningPoolCumulativeBean = poolReportMiningPoolCumulativeDao.getPoolReportMiningPoolCumulativeBean(arg.getDay());
        return ResultBean.ok(poolReportMiningPoolCumulativeBean);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolBean>> summary(DayTotalArg arg) {
        List<PoolReportMiningPoolBean> list = poolReportMiningPoolDao.getGreaterThanOrEqualTo(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolSelfBean>> self(DayTotalArg arg) {
        List<PoolReportMiningPoolSelfBean> list = poolReportMiningPoolSelfDao.getGreaterThanOrEqualTo(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolPartnerBean>> partner(DayTotalArg arg) {
        List<PoolReportMiningPoolPartnerBean> list = poolReportMiningPoolPartnerDao.getGreaterThanOrEqualTo(arg.getDay());
        return ResultBean.ok(list);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolGunBean>> gun(DayTotalArg arg) {
        List<PoolReportMiningPoolGunBean> poolReportMiningPoolGunBeans = poolReportMiningPoolGunDao.getGreaterThanOrEqualTo(arg.getDay());
        return ResultBean.ok(poolReportMiningPoolGunBeans);
    }

    @Override
    public ResultBean<List<PoolReportMiningPoolCumulativeBean>> cumulative(DayTotalArg arg) {
        List<PoolReportMiningPoolCumulativeBean> poolReportMiningPoolCumulativeBeans = poolReportMiningPoolCumulativeDao.getGreaterThanOrEqualTo(arg.getDay());
        return ResultBean.ok(poolReportMiningPoolCumulativeBeans);
    }
}
