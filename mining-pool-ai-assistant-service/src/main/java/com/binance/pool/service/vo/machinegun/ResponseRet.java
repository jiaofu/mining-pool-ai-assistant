package com.binance.pool.service.vo.machinegun;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseRet<T> {
    private T data;
    private String message;
    private String status;
}
