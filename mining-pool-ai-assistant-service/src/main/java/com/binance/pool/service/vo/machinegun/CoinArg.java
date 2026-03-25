package com.binance.pool.service.vo.machinegun;


import com.binance.pool.service.vo.validator.HarmlessString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoinArg {

    @Size(max = 20)
    @HarmlessString
    private String coin;
}
