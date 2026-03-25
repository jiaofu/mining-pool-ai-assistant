package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yyh
 * @date 2020/3/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum PaymentStatusEnum {

    WAIT_PAY("未支付",0),
    PAYING("支付中",1),
    PAID("已支付",2);
    private String desc;
    private Integer status;

}
