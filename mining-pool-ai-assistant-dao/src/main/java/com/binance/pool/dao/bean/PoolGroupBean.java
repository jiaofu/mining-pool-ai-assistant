package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Table(name = "pool_group")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoolGroupBean extends  BaseBean {

    private Long puid; //'puid',
    private Long algoId;
    private String groupName; // '组别名称,
    private Integer status; // '0:正常， 1：删除',

}
