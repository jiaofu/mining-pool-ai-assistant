package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolReportSavingsInterestBean;
import com.binance.pool.dao.mapper.pool.PoolReportSavingsInterestMapper;
import com.binance.pool.dao.pool.PoolReportSavingsInterestDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class PoolReportSavingsInterestDaoImpl implements PoolReportSavingsInterestDao {

    @Resource
    PoolReportSavingsInterestMapper mapper;

    @Override
    public List<PoolReportSavingsInterestBean> getPoolReportSavingsInterests() {
        List<PoolReportSavingsInterestBean> recordBeans = Lists.newArrayList();
        long start = System.currentTimeMillis();
        int pageSize = 5000;
        for (int page = 0; ; page++) {
            Example example = new Example(PoolReportSavingsInterestBean.class);

            example.createCriteria().andGreaterThan("uidPoolBinance", 0);
            int startIndex = page * pageSize;
            if (startIndex < 0) {
                startIndex = 0;
            }
            String order = String.format("id desc limit %s, %s", startIndex, pageSize);
            example.setOrderByClause(order);
            List<PoolReportSavingsInterestBean> beans = mapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(beans)) {
                recordBeans.addAll(beans);
                log.info("getPoolReportSavingsInterests get tempBeans:{} totalBeans:{} total time:{}ms.", beans.size(), recordBeans.size(), System.currentTimeMillis() - start);
            }
            if (CollectionUtils.isEmpty(beans) || beans.size() < pageSize) {
                break;
            }

        }
        log.info("getPoolReportSavingsInterests final totalBeans:{} total time:{}ms.", recordBeans.size(), System.currentTimeMillis() - start);
        return recordBeans;
    }

    @Override
    public PoolReportSavingsInterestBean getPoolReportSavingsInterestBean(Long uidPoolBinance) {
        Example example = new Example(PoolReportSavingsInterestBean.class);
        example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance);
        example.setOrderByClause(" id desc limit 1 ");

        return mapper.selectOneByExample(example);
    }

}
