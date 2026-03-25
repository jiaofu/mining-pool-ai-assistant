package com.binance.pool.service.cache.Impl;

import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import com.binance.pool.dao.pool.PoolBinanceUserDao;
import com.binance.pool.dao.pool.PoolRequestApiConfigDao;
import com.binance.pool.dao.pool.UserDao;
import com.binance.pool.service.cache.UserCacheServer;
import com.binance.pool.service.config.SysConfig;
import com.binance.pool.service.util.AesUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserCacheServerImpl implements UserCacheServer {


    @Resource
    UserDao userDao;
    @Resource
    PoolBinanceUserDao poolBinanceUserDao;


    @Resource
    PoolRequestApiConfigDao poolRequestApiConfigDao;


    @Resource
    SysConfig sysConfig;


    public LoadingCache<Long, PoolBinanceUserBean> userPoolUserId = CacheBuilder.newBuilder()
            .maximumSize(100000)//设置容量上限
            .initialCapacity(10)
            .expireAfterAccess(5, TimeUnit.MINUTES)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(5, TimeUnit.MINUTES) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<Long, PoolBinanceUserBean>() {
                @Override
                public PoolBinanceUserBean load(Long key) throws Exception {

                    PoolBinanceUserBean poolBinanceUserBean = poolBinanceUserDao.getPoolBinanceUserBeanByUid(key);
                    if(poolBinanceUserBean == null){
                        poolBinanceUserBean = PoolBinanceUserBean.builder().build();
                    }
                    return poolBinanceUserBean;
                }
            });

    public LoadingCache<String, PoolRequestApiConfigBean> apiConfig = CacheBuilder.newBuilder()
            .maximumSize(100000)//设置容量上限
            .initialCapacity(10)
            .expireAfterAccess(1, TimeUnit.HOURS)//若秒内没有读写请求则进行回收
            .refreshAfterWrite(2, TimeUnit.HOURS) // 实现缓存的自动刷新
            .concurrencyLevel(Runtime.getRuntime().availableProcessors())
            .build(new CacheLoader<String, PoolRequestApiConfigBean>() {
                @Override
                public PoolRequestApiConfigBean load(String key) throws Exception {

                    PoolRequestApiConfigBean poolRequestApiConfigBean = poolRequestApiConfigDao.getPoolRequestApiConfigBeans(key);
                    getUserSecret(poolRequestApiConfigBean);
                    return poolRequestApiConfigBean;
                }
            });





    @Override
    public PoolBinanceUserBean getPoolBinanceUserBean(long uidBinance) {
        try {
            return userPoolUserId.getUnchecked(uidBinance);
        } catch (Exception ex) {
            log.error(" getPoolBinanceUserBean 获取用户为null，uidBinance 为：" + uidBinance, ex);
        }
        return null;
    }
    @Override
    public PoolRequestApiConfigBean getPoolRequestApiConfigBean(String key) {
        try {
            return apiConfig.getUnchecked(key);

        } catch (Exception ex) {
            PoolRequestApiConfigBean poolRequestApiConfigBean = poolRequestApiConfigDao.getPoolRequestApiConfigBeans(key);
            getUserSecret(poolRequestApiConfigBean);
            return poolRequestApiConfigBean;
        }

    }


    private void getUserSecret(PoolRequestApiConfigBean bean){

        StringBuilder strLog = new StringBuilder();
        try {
            if(bean == null){
                return ;
            }
            strLog.append(" getUserSecret ");
            strLog.append(" getDbSecretkey:" +sysConfig.getDbSecretkey());
            strLog.append(" getSecretKey:"+  bean.getSecretKey());
            String totpKey = AesUtil.decrypt( bean.getSecretKey(),sysConfig.getDbSecretkey());
            bean.setGetUserSecret(totpKey);
        }catch (Exception ex){
            log.error(" 出现异常"+strLog.toString(),ex);
        }

    }



}
