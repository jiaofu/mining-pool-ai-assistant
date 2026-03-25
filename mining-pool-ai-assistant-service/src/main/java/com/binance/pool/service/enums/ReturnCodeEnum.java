package com.binance.pool.service.enums;


/**
 * 业务返回码
 */
public enum ReturnCodeEnum {

    SUCCESS(0, "成功","success"),
    DATA_NOT_OVER(400, "data not calculate over","pending."),
    FAIL(-1, "失败","fail"),
    SYSTEM_ERROR(-3, "系统异常","fail"),




    //MISS_PARAMETER(-1102, "参数错误",""),
    TIME_ERROR_CALL(-1021, "超时调用",""),
    USER_APPID_ERROR(-1021, "身份获取失败",""),
    IIIEGAL_METHOD_CALL(-6, "method非法调用",""),
    IIIEGAL_IP_CALL(-5, "ip非法调用",""),
    AUTHENTICATION_FAIL(-1022, "身份验证失败",""),



    IP_FREEZE_FAIL_NOT_WEBSOCKET(-1003, "ip冻结",""),
    SPEED_FAIL_NOT_WEBSOCKET(-1003, "限速",""),

    SPEED_FAIL_A(-1003, "限速",""),

    INTERFACE_NOT_ACCESSIBLE(-100003, "接口不可以访问错误", "Interface not accessible"),



    COIN_ERROR(-4000011,"币种错误",""),
    PAGE_ERROR(-4000012,"分页参数错误，没有那么多数据",""),

    UUID_POOL_BINANCE_MISS_ERROR(-4000013,"账户没有矿池账户id",""),
    REQUEST_METHOD_ERROR(-22, "请求类型错误",""),
    PARAM_ILLEGAL(-23, "参数不合法",""),
    PARAM_MISS_PARAMETER(-24, "参数缺少字段'%s'",""),
    PARAM_TYPE_PARAMETER(-25, "参数类型字段错误'%s'",""),

    PARAM_ERROR_PARAMETER(-24, "参数字段'%s'不合法","");

    private int code;
    private String desc;
    private String message;

    ReturnCodeEnum(int code, String desc, String message) {
        this.code = code;
        this.desc = desc;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
