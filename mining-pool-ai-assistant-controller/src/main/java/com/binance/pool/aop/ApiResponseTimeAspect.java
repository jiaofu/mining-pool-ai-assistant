package com.binance.pool.aop;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ApiResponseTimeAspect {

    private final MeterRegistry meterRegistry;

    public ApiResponseTimeAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // 切点：拦截所有Controller层的接口方法
    @Pointcut("execution(* com.binance.pool.controller..*(..)) && " +
            "( @annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) )")
    public void apiPointcut() {}

    // 环绕通知：统计接口执行时间
    @Around("apiPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1. 获取接口唯一标识（类名+方法名）
        String api = joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName();

        // 2. 开始计时
        Timer.Sample sample = Timer.start(meterRegistry);

        try {
            // 3. 执行接口方法
            return joinPoint.proceed();
        } finally {
            // 4. 结束计时并记录响应时间（无论成功失败都统计）
            sample.stop(Timer.builder("api_response_time_seconds")
                    .tag("api", api)
                    .description("API接口响应时间（秒）")
                    .register(meterRegistry));
        }
    }
}
