package com.binance.pool.service.exception;

import lombok.Data;

@Data
public class PoolParameterException extends Exception{
    private int code;
    public PoolParameterException(int code, String msg){
        super(msg);
        this.code=code;
    }

}
