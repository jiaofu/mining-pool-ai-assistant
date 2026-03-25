package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yyh
 * @date 2020/3/12
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MinerWorkerSortColumn {

    WORKER_NAME("workerName",1),
    HASH_RATE("hashRate",2),
    DAY_HASH_RATE("dayHashRate",3),
    REJECT_RATE("rejectRate",4),
    LAST_TIME("lastShareTime",5);
    private String desc;
    private Integer status;
    public static MinerWorkerSortColumn parseEnum(Integer status){
        for (MinerWorkerSortColumn a : values()) {
            if (a.status.equals(status) ) {
                return a;
            }
        }
        return WORKER_NAME;
    }

}
