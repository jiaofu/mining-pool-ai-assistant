package com.binance.pool.service.util;

import okhttp3.internal.Util;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RedisKeyUtil {

    /**
     * puid和poolUsername对应关系
     */
    public static final String PUID_NAME_MAP = "PUID_NAME_MAP";

    /**
     * puid和poolUsername对应关系，iterm 为puid
     */
    public static final String PUID_NAME_MAP_KEY = "PUID_NAME_MAP_KEY_%s";

    /**
     * 用户算力
     */
    public static final String BINANCE_USER_HASHRARE="BINANCE_USER_HASHRARE_";

    /**
     * 首页缓存
     */
    public static final String BOOL_MAIN_INDEX="BOOL_INDEX";


    /**
     * 统计页 缓存
     */
    public  static final  String  BOOL_STA_INDEX = "BOOL_STA_INDEX_";

    /**
     * 分组信息缓存Map
     */
    public static final String GROUP_CACHE_MAP = "GROUP_CACHE_MAP";
    /**
     * 状态信息缓存Map
     */
    public static final String STATUS_CACHE_MAP = "STATUS_CACHE_MAP";
    /**
     * 矿工信息缓存Map
     */
    public static final String WORKER_CACHE_MAP = "WORKER_CACHE_MAP";
    /**
     * 矿工信息缓存Map key
     */
    public static final String HASHRATE_CACHE_MAP_KEY = "HASHRATE_CACHE_MAP_KEY_%s_%s";

    /**
     * 算法的redis
     */
    public static final String ALGO_LIST = "ALGO_LIST";

    /**
     * symbol的redis
     */
    public static final String COIN_LIST = "COIN_LIST";

    /**
     * symbol和算法的的redis
     */
    public static final String ALGO_COIN_LIST = "ALGO_COIN_LIST";

    /**
     * 报警配置信息
     */
    public static final String WARING_CONFIG_INFO = "WARING_CONFIG_INFO";
    /**网络矿工占收益百分比*/
    public static final String NET_FEE_RATE = "NET_FEE_RATE_%s";
    /**
     * wss连接 key 下划线分割，中间是puid，最后是algoId
     */
    public static final String POOL_LISTEN_KEN_KEY = Constants.WSS_TOKEN_PREFIX+"_%s_%s";
    /**
     * 保存用户上次查询的矿机workerId的 key
     */
    public static final String WORKERS_PUSH_KEY = "WORKERS_PUSH_KEY_%s_%s";

    /**
     * 保存用户矿机的分组id
     */
    public static final String WORKER_GROUP_KEY = "WORKER_GROUP_KEY_%s_%s";
    /**
     * worker的kline数据
     */
    public static final String WORKER_KLINE_KEY = "WORKER_KLINE_KEY_%s_%s";
    /**
     * uidBinance 和 uidPoolBinance的对应关系
     */
    public static final String POOL_BINANCE_KEY = "POOL_BINANCE_KEY_%s";

    /**
     * 昨日收益
     */
    public static final String STAT_YESTERDAY_PROFIT = "STAT_YESTERDAY_PROFIT";

    /**
     * 今日收益
     */
    public static final String STAT_TODAY_PROFIT = "STAT_TODAY_PROFIT";
    /**
     * 节点矿工列表redis
     */
    public static final String POOL_WORKER_LIST_KEY = "POOL_WORKER_LIST_KEY_%s";
    /**
     * 节点币种下矿工列表
     */
    public static final String WORKER_HASH_CACHE_KEY = "WORKER_HASH_CACHE_KEY_%s";
    /**
     * 矿工每日历史曲线
     */
    public static final String WORKER_DAY_HASH_KEY = "WORKER_DAY_HASH_KEY_%s_%s";
    //当日数据是否计算完毕的标志
    public static final String WORKER_DAY_FLAG_KEY = "WORKER_DAY_FLAG_KEY_%s_%s";
    public static final String WORKER_DAY_ITEM_KEY = "WORKER_DAY_ITEM_KEY_%s_%s";
    /**
     * 矿工小时历史曲线
     */
    public static final String WORKER_HOUR_HASH_KEY = "WORKER_HOUR_HASH_KEY_%s_%s";
    public static final String WORKER_HOUR_FLAG_KEY = "WORKER_HOUR_FLAG_KEY_%s_%s";
    public static final String WORKER_HOUR_ITEM_KEY = "WORKER_HOUR_ITEM_KEY_%s_%s";
    /**
     * 更新数据的时间
     */
    public static final String POOL_WORKER_LAST_TIME = "POOL_WORKER_LAST_TIME";
    /**
     * 矿池总活跃矿工数量
     */
    public static final String WORKER_POOL_VALID_KEY = "WORKER_POOL_VALID_KEY_%s";
    /**
     * 当日账单是否加载完毕
     */
    public static final String EARN_OVER_KEY = "EARN_OVER_KEY_%s_%s";
    public static final String EARN_DATA_KEY = "EARN_DATA_KEY_%s_%s";


    /**
     * 多节点合并后的天数据
     */
    public static final String CURRENCY_DAY_KEY = "CURRENCY_DAY_KEY_%s_%s";


    /**
     * 第三方需要的机枪池数据是否获取完毕
     */
    public static final String GUN_EARN_KEY = "GUN_EARN_KEY_%s";
    /**
     * 返回矿工信息缓存Map key
     * @param puid
     * @param algoId
     * @return
     */
    public static String getHashrateCacheMapKey(Long puid,Long algoId){
        return String.format(HASHRATE_CACHE_MAP_KEY,String.valueOf(puid),String.valueOf(algoId));
    }


    public static String getListenKey(Long puid, Long algoId) {
        return String.format(POOL_LISTEN_KEN_KEY,String.valueOf(puid),String.valueOf(algoId));
    }

    public static String getWorkersPushKey(Long puid, Long algoId) {
        return String.format(WORKERS_PUSH_KEY,String.valueOf(puid),String.valueOf(algoId));
    }


    public static String getNetFeeRate(Long day) {
        return String.format(NET_FEE_RATE,String.valueOf(day));
    }

    public static String getWorkerGroupKey(Long puid, String workerId) {
        return String.format(WORKER_GROUP_KEY,String.valueOf(puid),String.valueOf(workerId));
    }

    public static String getKlineKey(Long puid, Long algoId) {
        return String.format(WORKER_KLINE_KEY,String.valueOf(puid),String.valueOf(algoId));
    }

    public static String getPoolBinanceKey(Long puid) {
        return String.format(POOL_BINANCE_KEY,String.valueOf(puid));

    }

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Util.threadFactory("OkHttp Dispatcher", false));
        executorService.execute(()->{
            System.out.println("23323333333");
        });
        System.out.println("232332");
    }

    public static String getWorkerListCacheKey(String puidName) {
        return String.format(POOL_WORKER_LIST_KEY,puidName);
    }
    public static String getWorkerHashCacheKey(String currency){
        return String.format(WORKER_HASH_CACHE_KEY,currency);
    }

    public static String getWorkerDayHashCacheKey(String currency,Long day) {
        return String.format(WORKER_DAY_HASH_KEY,currency,String.valueOf(day));

    }
    public static String getWorkerDayHashItemKey(String currency,String puidName) {
        return String.format(WORKER_DAY_ITEM_KEY,currency,puidName);

    }

    public static String getWorkerHourHashCacheKey(String currency,Long hour) {
        return String.format(WORKER_HOUR_HASH_KEY,currency,String.valueOf(hour));

    }
    public static String getWorkerHourHashItemKey(String currency,String puidName) {
        return String.format(WORKER_HOUR_ITEM_KEY,currency,puidName);

    }

    public static String getWorkerDayFlagKey(String currency, Long day) {
        return String.format(WORKER_DAY_FLAG_KEY,currency,String.valueOf(day));
    }

    public static String getWorkerHourFlagKey(String currency, Long hour) {
        return String.format(WORKER_HOUR_FLAG_KEY,currency,String.valueOf(hour));
    }

    public static String getValidWorker(String currency) {
        return String.format(WORKER_POOL_VALID_KEY,currency);
    }

    public static String getEarnOverKey(String currency, Long day) {
        return String.format(EARN_OVER_KEY,currency,String.valueOf(day));
    }
    public static String getEarnDataKey(String currency, Long day) {
        return String.format(EARN_DATA_KEY,currency,String.valueOf(day));
    }
    public static String getPuidNameMapKey(Long puid){
        return String.format(PUID_NAME_MAP_KEY,String.valueOf(puid));
    }
    public static String getCurrencyDayKey(String currency, Long day) {
        return String.format(CURRENCY_DAY_KEY,currency,String.valueOf(day));
    }

    public static String getGunEarnKey(Long day) {
        return String.format(GUN_EARN_KEY,String.valueOf(day));
    }
}
