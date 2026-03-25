package com.binance.pool.service.vo.machinegun;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineGunVo {

    private String appId;
    private String sign;
    private String appName;
    private String timestamp;

}
