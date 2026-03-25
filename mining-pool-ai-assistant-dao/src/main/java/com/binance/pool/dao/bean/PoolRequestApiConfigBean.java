package com.binance.pool.dao.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "pool_request_api_config")
public class PoolRequestApiConfigBean {

    private String appId; // '账户id',
    private String appName; // '账户name',
    private String secretKey; // COMMENT '账户密码',
    private String remarks; // '角色id',
    private String status; // '0 正常 1:删除',

    @Transient
    private String getUserSecret;
}
