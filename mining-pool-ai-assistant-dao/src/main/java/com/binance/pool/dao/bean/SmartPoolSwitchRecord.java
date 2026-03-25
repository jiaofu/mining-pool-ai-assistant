package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by yyh on 2020/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "smart_pool_switch_record")
public class SmartPoolSwitchRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long algoId;
    private String coinName;//要切成什么币种
    private Integer users;//影响多少用户
    private Long day;
    private Long switchTime;//什么时候切换的
    private Long responseTime;//切换接口耗时
    private Integer status;//切换状态 0全部失败，1：全部成功  2：部分成功
    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',
}
