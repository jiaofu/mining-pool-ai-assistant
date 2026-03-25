package com.binance.pool.service.vo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验的非空字符串必须为为整数
 * Created by yyh on 2020/4/6
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IntegerStringValidator.class}
)
public @interface IntegerString {
    boolean required() default false;

    String message() default "not number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
