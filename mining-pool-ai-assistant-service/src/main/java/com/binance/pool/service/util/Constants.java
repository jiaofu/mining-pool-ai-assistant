package com.binance.pool.service.util;

import java.math.BigDecimal;

/**
 * @author yyh
 * @date 2020/3/11
 */
public class Constants {
    public static final String CHINA_SOUTH = "chinaSouth";
    public static final String CHINA_NORTH = "chinaNorth";
    public static final String USA = "usa";
    public static final String EUROPE = "europe";

    public static final String API_CHINA_SOUTH = "hz";
    public static final String API_CHINA_NORTH = "huhehaote";
    public static final String API_EUROPE = "eu";
    //api方式获取数据
    public static final String API_DATA_TYPE = "btcComApi";
    //自研矿池数据库
    public static final String BINANCE = "binance";
    public static final String BTCCOM_BTC_CURRENCY = "btc";
    public static BigDecimal satoshi = new BigDecimal("0.00000001");
    public static final long DEFAULT_GROUP_ID = -1l;
    public static final long ALL_GROUP = -2l;
    public static final Integer KLNIE_HOUR = 25;
    public static final Integer KLNIE_DAY = 31;
    public static final String CACHE_SPLIT = "-";
    public static final Long DELETE_WORKER_GROUP = 0l;
    //24分钟未提交算力为失效
    public static final long INVALID_WORKER_MINUTE = 24*60;
    //10分钟未提交算力为不活跃
    public static final long OFFLINE_WORKER_MINUTE = 15;
    //默认分页大小
    public static final int PAGE_SIZE = 1000;
    public static final int MAX_PAGE_SIZE = 3000;
    //日平均算力选24小时平均
    public static final int AVG_DAY_HOUR_HASH = 24;

    //默认分发大小
    public static final int DIVIDEND_SIZE = 1000;
    /**
     * 发送时间间隔
     */
    public static final long SNED_TIME_TNTERVAL = 1000*60*30;
    /**
     * 默认FPPS费率
     */
    public static final BigDecimal DEFAULT_FPPS_RATE = new BigDecimal("0.025");
    /**
     * 默认PPS费率
     */
    public static final BigDecimal DEFAULT_PPS_RATE = new BigDecimal("0.04");
    /**
     * 联合挖矿发放给用户的总金额
     */
    public static final BigDecimal UNION_MINER_USER_GIVE = new BigDecimal("0.96");


    /**矿工页面数据redis订阅频道*/
    public static final String MINER_WORKER_CHANNEL = "MINER_WORKER_CHANNEL";
    /**统计页面数据redis订阅频道*/
    public static final String STATISTIC_CHANNEL = "STATISTIC_CHANNEL";


    /**主页页面数据redis订阅频道*/
    public static final String INDEX_CHANNEL = "INDEX_CHANNEL";
    /**用户算力数据redis订阅频道*/
    public static final String USER_HASHRATE_CHANNEL = "USER_HASHRATE_CHANNEL";

    public static final String WSS_TOKEN_PREFIX = "PLNT";

    public static final String ENCODED_UTF8 = "UTF-8";
    public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String UID_POOL_BINANCE = "UID_POOL_BINANCE";
    public static final String UID_BINANCE = "userId";
    /**
     * 难度的取值的数量
     */
    public static final int blockInfoDiffLimit = 432;

    /**
     * 系统认为不会超过的algoId,coinId
     */
    public static final long MAX_HUMAN_ALGO_COIN = 40;
    /**
     * 图表的取之数量
     */
    public static final int chartDataLimit =30;

    /**系统认为不会超过的puid值,groupID*/
    public static final long MAX_HUMAN_VALUE = 100_0000_0000_0000l;

    //  对接接口
    public static final String APP_ID = "app_id";
    public static final String SIGN = "sign";
    public static final String APP_NAME = "app_name";
    public static final String TIMESTAMP = "timestamp";
    //默认是 60s 就是 60000
    public static final int DEFAULT_RECVWINDOW = 60000;//5s

    public static final String TOPIC_POOL_SAVINGS_EARN= "TOPIC_POOL_SAVINGS_EARN";
    public static final String TOPIC_PUID = "TOPIC_PUID";
    public static final String TOPIC_TRUSTEESHIP_PUID = "TOPIC_TRUSTEESHIP_PUID";
    public static final String TOPIC_DEL_WORKERS = "TOPIC_DEL_WORKERS";
    public static final String TOPIC_MODIFY_GROUP_CACHE = "MODIFY_GROUP_CACHE";
    public static final String TOPIC_DEL_GROUP = "TOPIC_DEL_GROUP";
    public static final String TOPIC_UPDATE_GROUP = "TOPIC_UPDATE_GROUP";
    public static final String TOPIC_MOVE_WORKER_GROUP = "TOPIC_MOVE_WORKER_GROUP";

    public static final String TOPIC_POOL_SAVINGS_EARN_INTERESTS= "TOPIC_POOL_SAVINGS_EARN_INTERESTS";
    public static final String APP_NAME_INVITATION = "invitation";
    public static final String APP_NAME_INVITATION_RETURNCOMMISSION = "invitation_returnCommission";
}
