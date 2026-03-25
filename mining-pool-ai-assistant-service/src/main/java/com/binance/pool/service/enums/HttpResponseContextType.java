package com.binance.pool.service.enums;

/**
 * Created by lzy on 2017/6/20.
 */
public enum HttpResponseContextType {
    XML("text/xml","XML"),
    TEXT("application/text", "文本"),
    BYTE("application/octet-stream","二进制流"),
    JSON("application/json", "json");

    private String value;
    private String displayName;

    private HttpResponseContextType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
