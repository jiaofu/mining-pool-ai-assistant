package com.binance.pool.dao.pool.impl;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolUserBean;
import com.binance.pool.dao.mapper.pool.BinanceUserMapper;
import com.binance.pool.dao.mapper.pool.UserMapper;
import com.binance.pool.dao.pool.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserMapper userMapper;

    @Resource
    BinanceUserMapper binanceUserMapper;

    @Override
    public List<PoolUserBean> getAllPoolUsers(int pageNum, int pageSize,int status) {
        Example example = new Example(PoolUserBean.class);
        example.createCriteria()
                .andEqualTo("status",status);
        int startIndex = pageNum*pageSize;
        if(startIndex<0){
            startIndex=0;
        }
        String order = String.format(" id asc limit %s, %s ", startIndex,pageSize);
        example.setOrderByClause(order);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<PoolUserBean> getUsersByBinanceId(long uidBinance) {
        return userMapper.getPoolAlgoCoinBeans(uidBinance);
    }

    @Override
    public List<PoolBinanceUserBean> getPoolBinanceUserBeans() {
        Example example = new Example(PoolBinanceUserBean.class);
        example.createCriteria()
                .andEqualTo("status",1);
        return binanceUserMapper.selectByExample(example);
    }

    @Override
    public PoolUserBean getPoolUserBeanById(long id) {
        Example example = new Example(PoolBinanceUserBean.class);
        example.createCriteria()
                .andEqualTo("id",id);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public Integer updateDataSource(Long id, String databases) {
        Example example = new Example(PoolUserBean.class);
        example.createCriteria()
                .andEqualTo("id",id);
        PoolUserBean update = PoolUserBean.builder()
                .puidDatabase(databases)
                .build();
        return userMapper.updateByExampleSelective(update,example);
    }
}
