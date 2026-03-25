package com.binance.pool.service.vo;

import com.binance.pool.service.enums.ReturnCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "返回结果")
public class ResultBean<T> {
    // code枚举 用于错误返回code msg
    @Schema(description = "code 为0 代表正常，负数代表请求错误 400表示数据未计算完毕")
    private int code;
    @Schema(description = "错误信息")
    private String msg;
    //数据
    private T data;

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultBean(ReturnCodeEnum returnCodeEnum, T data) {
        this.code = returnCodeEnum.getCode();
        this.msg = returnCodeEnum.getDesc();
        this.data = data;

    }

    public static ResultBean error(ReturnCodeEnum returnCodeEnum) {
        return new ResultBean(returnCodeEnum, null);
    }


    public static ResultBean error(int code, String msg) {
        return new ResultBean(code, msg, null);
    }


    public static ResultBean ok(Object data) {
        return new ResultBean(ReturnCodeEnum.SUCCESS.getCode(), "", data);
    }

    public static ResultBean errorWithFromMsg(ReturnCodeEnum returnCodeEnum, String str) {

        String msg = String.format(returnCodeEnum.getDesc(), str);
        return new ResultBean(returnCodeEnum.getCode(), msg, null);
    }


}
