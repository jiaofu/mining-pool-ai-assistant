package com.binance.pool.service.vo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验的非空字符串是否包括非法字符
 * Created by yyh on 2020/4/6
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
    validatedBy = {HarmlessStringValidator.class}
)
public @interface HarmlessString {
    boolean required() default false;

    String message() default "invalid string";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
