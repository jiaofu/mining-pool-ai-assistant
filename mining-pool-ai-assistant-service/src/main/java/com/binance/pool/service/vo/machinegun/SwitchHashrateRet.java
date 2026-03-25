package com.binance.pool.service.vo.machinegun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SwitchHashrateRet {

    private String status;
    private String region;
    private SwitchHashrateUsercoinRet usercoin;
}
