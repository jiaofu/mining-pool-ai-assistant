package com.binance.pool.service.vo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Created by yyh on 2020/4/6
 */
public class PastOrPresentLongValidator implements ConstraintValidator<PastOrPresentLong, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            //可以不传
            return true;
        }
        return value <= System.currentTimeMillis();
    }
}