package com.binance.pool.dao.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 算法表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_algo")
public class PoolAlgoBean extends  BaseBean {

    private String name; //'名称,
    private Integer status; //'0:使用 1:停止',
    private Integer poolIndex;//排序,0
    private Integer rateType; // 选择费率  0 使用pps计算费率，1 使用fpps计算费率
    private BigDecimal ppsRate;// 'pps 费率默认值 ',
    private BigDecimal fppsRate;// 'fpps 费率默认
    private String unit;//  单位


}
