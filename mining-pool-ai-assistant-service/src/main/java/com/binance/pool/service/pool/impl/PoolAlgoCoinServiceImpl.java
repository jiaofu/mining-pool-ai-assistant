package com.binance.pool.service.pool.impl;

import com.binance.pool.dao.bean.PoolAlgoBean;
import com.binance.pool.dao.bean.PoolAlgoCoinBean;
import com.binance.pool.dao.bean.PoolCoinBean;
import com.binance.pool.dao.pool.AlgoCoinDao;
import com.binance.pool.service.enums.PoolAlgoCoinEnum;
import com.binance.pool.service.enums.PoolAlgoEnum;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.pool.PoolAlgoCoinService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class PoolAlgoCoinServiceImpl implements PoolAlgoCoinService {
    @Resource
    AlgoCoinDao algoCoinDao;


    @Override
    public List<PoolAlgoCoinBean> getPoolAlgoCoinBeans() {
        return algoCoinDao.getPoolAlgoCoinBeans(0);
    }

    @Override
    public List<PoolCoinBean> getPoolCoinBeans() {
        return algoCoinDao.getPoolCoinBeans(0);
    }

    @Override
    public List<PoolAlgoBean> getPoolAlgoBeans() {
        return algoCoinDao.getPoolAlgoBeans(0);
    }


    @Override
    public void refreshMemory() {

        PoolAlgoEnum.INSTANCE.updateOptionMemory();
        PoolCoinEnum.INSTANCE.updateOptionMemory();
        PoolAlgoCoinEnum.INSTANCE.updateOptionMemory();
    }
}
