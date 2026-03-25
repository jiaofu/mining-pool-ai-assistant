package com.binance.pool.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.binance.pool.service.cache.CoinCacheService;
import com.binance.pool.service.enums.PoolAlgoCoinEnum;
import com.binance.pool.service.enums.PoolAlgoEnum;
import com.binance.pool.service.enums.PoolCoinEnum;
import com.binance.pool.service.pool.PoolAlgoCoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Slf4j
@Component

public class BaseDataConfig implements ApplicationRunner {



    @Resource
    PoolAlgoCoinService poolAlgoCoinService;

    @Resource
    CoinCacheService coinCacheService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ParserConfig.getGlobalInstance().setSafeMode(true);
        PoolAlgoEnum.INSTANCE.setService(poolAlgoCoinService);
        PoolCoinEnum.INSTANCE.setService(poolAlgoCoinService);
        PoolAlgoCoinEnum.INSTANCE.setService(poolAlgoCoinService);
        coinCacheService.refreshMemory();
    }
}