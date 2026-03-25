package com.binance.pool.service.vo;

import com.binance.pool.service.enums.ReturnCodeEnum;
import lombok.Data;

@Data
public class BussinessException extends Exception{
    private int code;
    private String msg;
    private ResultBean bean;
    public BussinessException(ResultBean bean){
        super(bean.getMsg());
        this.bean = bean;
    }
    public BussinessException(ReturnCodeEnum returnCodeEnum){
        bean = ResultBean.error(returnCodeEnum);
    }

}
