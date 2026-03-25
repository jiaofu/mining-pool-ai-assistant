package com.binance.pool.aop;

import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.vo.ResultBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@Aspect
@Component

public class ApiStatisticsAspect {

    private final MeterRegistry meterRegistry;
    private final ObjectMapper objectMapper;

    public ApiStatisticsAspect(MeterRegistry meterRegistry, ObjectMapper objectMapper) {
        this.meterRegistry = meterRegistry;
        this.objectMapper = objectMapper;
    }

    // 切点：拦截所有Controller层的接口方法（包含@RequestMapping及其派生注解如@GetMapping）
    @Pointcut("execution(* com.binance.pool.controller..*(..)) && " +
            "( @annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping) )")
    public void apiPointcut() {
        log.info("apiPointcut Pointcut");
    }

    // 统计正常返回的接口（含code=0和code≠0的情况）
    @AfterReturning(pointcut = "apiPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = null;
        Exception ex = null;



        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = attributes.getRequest();
            // 使用 request 对象
            log.info(" afterReturning Request URL: " + request.getRequestURL());
        }
        String requestUrl = request.getRequestURI();
        String status = resolveStatusFromResult(result);
        incrementCounter(requestUrl, status);
    }


    // 统计抛出异常的接口（包括@Validated校验失败和业务异常）
    @After("execution(* com.binance.pool.errorhandle.PoolApiExceptionResolver.resolveException(..)) ")
    public  void exceptUrl(JoinPoint p) throws Throwable{


        HttpServletRequest request = null;
        Exception ex = null;


        ex = (Exception) p.getArgs()[p.getArgs().length - 1];

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = attributes.getRequest();
            // 使用 request 对象
            log.info(" exceptUrl Request URL: " + request.getRequestURL());
        }
        String requestUrl = request.getRequestURI();
        if(ex instanceof NoResourceFoundException && requestUrl.equalsIgnoreCase("/")){
            incrementCounter(requestUrl, "success");
        }else{
            // 所有异常统一标记为error（也可根据异常类型细分标签）
            incrementCounter(requestUrl, "error");
        }

    }



    // 获取接口唯一标识（类名+方法名）
    private String getApiIdentifier(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName();
    }



    // 从返回结果中解析code字段，判断状态
    private String resolveStatusFromResult(Object result) {
        try {

            Object data = null;
            // 判断结果类型并处理成功状态
            boolean isSuccess = false;
            if (result instanceof ResultBean) {
                ResultBean resultBean = (ResultBean) result;
                isSuccess = resultBean.getCode()- ReturnCodeEnum.SUCCESS.getCode()==0;
                if (isSuccess) {
                    data = resultBean.getData();
                }
//            } else if (result instanceof AutoregRet) {
//                AutoregRet autoregResult = (AutoregRet) result;
//                isSuccess = "success".equals(autoregResult.getStatus());
//                if (isSuccess) {
//                    data = autoregResult.getData();
//                }
            } else {
                // 处理其他类型的返回结果（如果有）
               log.info(" resolveStatusFromResult 未处理的返回结果类型：" + result.getClass().getName());
               //默认先返回success
//                return "error";
                return "success";
            }


            return (isSuccess) ? "success" : "error";
        } catch (Exception e) {
            // 解析失败（如返回格式不符合预期）也视为error
            return "error";
        }
    }

    // 通用的计数器递增方法
    private void incrementCounter(String api, String status) {
        Counter.builder("api_requests_total") // 指标名称
                .tag("api", api) // // 接口标签
                .tag("status", status) //  // 状态标签
                .register(meterRegistry) //   // 注册到指标注册表
                .increment(); //   // 计数+1
    }
}
