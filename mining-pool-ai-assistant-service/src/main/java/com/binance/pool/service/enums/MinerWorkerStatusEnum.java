package com.binance.pool.service.enums;

import com.binance.pool.service.util.Constants;
import com.binance.pool.service.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 矿机状态枚举
 * @author yyh
 * @date 2020/3/12
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MinerWorkerStatusEnum {
    ALL("全部",0),
    VALID("有效",1),
    OFFLINE("无效",2),
    INVALID("失效",3);
    private String desc;
    private Integer status;
    public static MinerWorkerStatusEnum parseEnum(Integer status){
        for (MinerWorkerStatusEnum a : values()) {
            if (a.status.equals(status) ) {
                return a;
            }
        }
        return null;
    }

    /**
     * 10未提交算力为不活跃
     * 24小时未提交算力为失效
     */
    public static Integer parseWorkerStatus(Date lastShareTime) {
        long min = DateUtil.betweenMinute(lastShareTime,new Date());
        if(min > Constants.INVALID_WORKER_MINUTE){
            return INVALID.getStatus();
        }else if(min > Constants.OFFLINE_WORKER_MINUTE){
            return OFFLINE.getStatus();
        }else{
            return VALID.getStatus();
        }
    }
}
