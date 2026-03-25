package com.binance.pool.service.vo.machinegun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "切换币种返回值")
public class SwitchCoinUserVo {

    private Integer err_no;
    private String err_msg;
    private String success;

}
