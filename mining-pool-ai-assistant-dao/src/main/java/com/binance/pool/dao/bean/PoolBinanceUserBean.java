package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_binance_user")
public class PoolBinanceUserBean extends  BaseBean {

    private Long uidBinance;//'binance 中 uid 表',？
    private Long uidPoolBinance;// uid 中矿池钱包的用户
    private String realName;//'用户真实姓名',,
    private String email;// '邮箱',
    private String phone;// '手机号',

}
