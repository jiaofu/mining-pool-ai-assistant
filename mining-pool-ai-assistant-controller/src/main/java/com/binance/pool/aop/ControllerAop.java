package com.binance.pool.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 1.用于监控controller 方法调用耗时
 */
@Aspect
@Configuration
@Slf4j
public class ControllerAop {

    @Around("execution(* com.binance.pool.controller..*(..)) ")
    public Object aroundController(ProceedingJoinPoint p) throws Throwable {
        StringBuilder strLog = new StringBuilder(" log ");

        //拦截的实体类
        Object target = p.getTarget();
        //拦截的方法名称
        String methodName = p.getSignature().getName();
        strLog.append(" 类名.方法:" + target.getClass().getSimpleName());
        strLog.append("." + methodName);

        Object[] args = p.getArgs();
        for (Object object : args) {
            if(object == null){
                continue;
            }
            String argName = object.getClass().getSimpleName().toLowerCase();
            if (argName.contains("arg")) {
                strLog.append("  参数类型:" + object.getClass().getSimpleName());
                strLog.append("  参数值:" + JSON.toJSONString(object));
            }
        }
        long start = System.currentTimeMillis();
        Object o = p.proceed();
        long end = System.currentTimeMillis();
        long cost = end - start;
        strLog.append("  time毫秒:" + cost);
        //if()
        strLog.append(" 结果:"+JSON.toJSONString(o));
        log.info(strLog.toString());
        return o;
    }


}
