package com.binance.pool.service.vo.machinegun;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

@Schema(description = "切换币种返回值")
public class SwitchCoinRet {



    @Schema(description = "0全部失败，1：全部成功  2：部分成功")
    private int status;
}
