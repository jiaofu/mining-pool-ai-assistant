package com.binance.pool.service.vo.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yyh on 2020/4/6
 * 校验时间戳必须为过去的一个时间
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
    validatedBy = {PastOrPresentLongValidator.class}
)
public @interface PastOrPresentLong {
    boolean required() default false;

    String message() default "invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
