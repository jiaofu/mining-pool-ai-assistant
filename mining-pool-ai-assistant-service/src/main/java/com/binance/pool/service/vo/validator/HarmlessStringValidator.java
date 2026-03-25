package com.binance.pool.service.vo.validator;

import com.binance.pool.service.util.StringUtil;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * 判断string 和 list<String>是否包含特殊字符
 * Created by yyh on 2020/4/6
 */
public class HarmlessStringValidator implements ConstraintValidator<HarmlessString, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            //可以不传
            return true;
        }
        if(value instanceof String){
            String str = (String) value;
            return !StringUtil.isSpecialChar(str);
        }else if(value instanceof List){
            List<String> strs = (List<String>) value;
            for(String str:strs){
                if(StringUtil.isSpecialChar(str)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}