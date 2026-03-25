package com.binance.pool.dao.bean.coinstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/4/7
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiningWorkerCheckBean {
    private Long puid;
    private Long num;
    private String database;

    public MiningWorkerCheckBean add(MiningWorkerCheckBean other) {
        if(other == null){
            return this;
        }
        if(this.puid-other.getPuid()!=0){
            throw new RuntimeException("MiningWorkerCheckBean puid not equal can not add ");
        }
        //一个数据源不会出现相同的puid,直接累加数据源
        this.database = this.database+","+other.getDatabase();
        return this;
    }
}
