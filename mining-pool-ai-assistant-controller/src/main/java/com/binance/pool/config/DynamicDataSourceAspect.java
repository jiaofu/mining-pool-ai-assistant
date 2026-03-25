package com.binance.pool.config;

import com.binance.pool.dao.config.DBAnno;
import com.binance.pool.dao.config.DBParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Describe: 动态数据源代理类
 */
@Aspect
@Component
@Order(value = -1)
public class DynamicDataSourceAspect {

    @Before("@annotation(com.binance.pool.dao.config.DBAnno)")
    public void beforeSwitchDS(JoinPoint point) {

        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();

        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = null;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);

            // 判断是否存在@DS注解
            if (method.isAnnotationPresent(DBAnno.class)) {
                DBAnno annotation = method.getAnnotation(DBAnno.class);
                int dbParamOffset = 0;
                mark:for(Parameter parameter:method.getParameters()){
                    for (Annotation annotationParam : parameter.getAnnotations()) {
                        if (annotationParam instanceof DBParam) {
                            dataSource = (String) point.getArgs()[dbParamOffset];
                            break mark;
                        }
                    }
                    dbParamOffset++;
                }

                if(dataSource == null){
                    // 取出注解中的数据源名
                    dataSource = annotation.value();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 切换数据源
        DataSourceContextHolder.setDataSource(dataSource);

    }

    @After("@annotation(com.binance.pool.dao.config.DBAnno)")
    public void afterSwitchDS(JoinPoint point) {

        DataSourceContextHolder.clearDataSource();

    }
}
