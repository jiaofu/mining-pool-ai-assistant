package com.binance.pool.dao.pool.impl;

import com.binance.pool.base.bean.PoolReportInvitationRecordBean;
import com.binance.pool.base.mapper.PoolReportInvitationRecordMapper;
import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.pool.PoolReportInvitationRecordDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Repository
public class PoolReportInvitationRecordDaoImpl implements PoolReportInvitationRecordDao {
    @Resource
    PoolReportInvitationRecordMapper mapper;

    @Override
    public List<PoolReportInvitationRecordBean> getPoolBinance(long uidPoolBinance) {
        Example example = new Example(PoolReportInvitationRecordBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("status",0);
        String order = String.format("modify_date  desc");
        example.setOrderByClause(order);
        return mapper.selectByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolReportInvitationRecordBean> getPoolBinance(long uidPoolBinance,Long algoId,String orderBy, Integer pageNum, Integer pageSize) {
        Example example = new Example(PoolReportInvitationRecordBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("algoId", algoId)
                .andEqualTo("status",0)
                .andEqualTo("coinId", 0);

        if(pageNum !=null && pageSize != null){
            int startIndex = (pageNum - 1) * pageSize;
            if (startIndex < 0) {
                startIndex = 0;
            }
            String order = String.format( orderBy + " limit %s, %s", startIndex, pageSize);
            example.setOrderByClause(order);
        }

        return mapper.selectByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public long getPoolBinanceCount(long uidPoolBinance, Long algoId) {
        Example example = new Example(PoolReportInvitationRecordBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("algoId", algoId)
                .andEqualTo("status",0)
                .andEqualTo("coinId", 0);
        return mapper.selectCountByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolReportInvitationRecordBean> getPoolBinanceByTime(long uidPoolBinance, Long algoId, Date start, Date end) {
        Example example = new Example(PoolReportInvitationRecordBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("algoId", algoId)
                .andEqualTo("status",0)
                .andEqualTo("coinId", 0)
                .andGreaterThanOrEqualTo("createdDate",start)
                .andLessThan("createdDate",end);
            String order = String.format( " created_date desc ");
            example.setOrderByClause(order);


        return mapper.selectByExample(example);
    }

    @DBAnno("dbRead")
    @Override
    public List<PoolReportInvitationRecordBean> getPoolBinanceByIds(long uidPoolBinance,Long algoId, List<Long> uidPoolBinanceInvitations) {
        Example example = new Example(PoolReportInvitationRecordBean.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("uidPoolBinance", uidPoolBinance)
                .andEqualTo("algoId", algoId)
                .andEqualTo("status",0)
                .andGreaterThan("coinId", 0)
                .andIn("uidPoolBinanceInvitation",uidPoolBinanceInvitations);

        String order = String.format("modify_date desc ");
        example.setOrderByClause(order);
        return mapper.selectByExample(example);
    }
}
