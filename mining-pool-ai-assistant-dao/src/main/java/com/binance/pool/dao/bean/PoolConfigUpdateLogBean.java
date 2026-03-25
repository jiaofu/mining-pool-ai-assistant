package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Table(name = "pool_config_update_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PoolConfigUpdateLogBean extends  BaseBean {

    private int operateId;// '操作人员id',
    private int operateType;//'操作类型：0 add, 1 update, 2 delete',
    private int operateAlgo;// '操作对象的所在库',
    private int operatePrimaryId;// '操作对象在所在表的主键id',
    private int businessType;//  '配置业务：0 矿池费率修改，1 返点比例修改，2报警人员修改',
    private String involveColumn;//  '修改的列,例如: rate_type,pps_rate',
    private String originValue;// '原值,例如:  0,0.04',
    private String newValue;// '新值,例如:  1,0.035'，

}
