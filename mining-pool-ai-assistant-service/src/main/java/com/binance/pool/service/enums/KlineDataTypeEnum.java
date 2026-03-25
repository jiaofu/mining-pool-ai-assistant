package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yyh
 * @date 2020/3/11
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum KlineDataTypeEnum {
    HOUR("24小时数据",0),
    DAY("30日数据",1);
    private String desc;
    private Integer dataType;
    public static KlineDataTypeEnum parseEnum(Integer dataType){
        for (KlineDataTypeEnum a : values()) {
            if (a.dataType.equals(dataType) ) {
                return a;
            }
        }
        return null;
    }

}
