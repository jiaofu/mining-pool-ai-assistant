package com.binance.pool.service.enums;

public enum CurrencyTypeEnum {
    BTCD(1, "btcd");

    private int type;

    private String name;

    CurrencyTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}