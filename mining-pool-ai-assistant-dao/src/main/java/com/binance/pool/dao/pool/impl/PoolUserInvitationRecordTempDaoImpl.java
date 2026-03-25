package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolUserInvitationRecordTempBean;
import com.binance.pool.base.mapper.PoolUserInvitationRecordTempMapper;
import com.binance.pool.dao.config.DBAnno;

import com.binance.pool.dao.pool.PoolUserInvitationRecordTempDao;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
@Slf4j
public class PoolUserInvitationRecordTempDaoImpl implements PoolUserInvitationRecordTempDao {

    @Resource
    PoolUserInvitationRecordTempMapper mapper;




    @Override
    @DBAnno(value = "dbRead")
    public PoolUserInvitationRecordTempBean getPoolUserInvitationRecordsLast(Long uidBinance, Long uidBinanceInvitation) {
        Example example = new Example(PoolUserInvitationRecordTempBean.class);
        example.createCriteria().andEqualTo("uidBinance",uidBinance).andEqualTo("uidBinanceInvitation",uidBinanceInvitation);
        return mapper.selectOneByExample(example);
    }
}
