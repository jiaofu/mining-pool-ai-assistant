package com.binance.pool.dao.pool;

import com.binance.pool.dao.bean.PoolUserHashMonitor;

import java.util.Date;
import java.util.List;

/**
 * Created by yyh on 2020/5/13
 */
public interface PoolUserHashMonitorDao {


  List<PoolUserHashMonitor> getPoolUserHashMonitors(List<Long> uidPoolList,Long algoId);
  PoolUserHashMonitor getPoolUserHashMonitors(long uidPoolBinance, Date lastShareTime);
}
