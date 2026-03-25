package com.binance.pool.interceptor;

import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import com.binance.pool.service.cache.UserCacheServer;
import com.binance.pool.service.config.PoolDataConfig;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.exception.PoolParameterException;
import com.binance.pool.service.util.ApiUtil;
import com.binance.pool.service.util.Constants;
import com.binance.pool.service.util.StringUtil;
import com.binance.pool.service.vo.ResultBean;
import com.binance.pool.service.vo.machinegun.InterceptorVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 身份验证拦截器
 */
@Slf4j
public class ApiInterceptorImpl  implements HandlerInterceptor {


    public ApiInterceptorImpl() {
        log.info("xxx");
    }


    @Resource
    UserCacheServer userCacheServer;

    private static String AppNameAME = "^[A-Za-z0-9]{2,32}$";
    /**
     * 调用前置拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        StringBuilder logBuider = new StringBuilder();
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            AccessAuth accessAuth = method.getAnnotation(AccessAuth.class);
            //没有注解登录类型
            if (accessAuth == null) {
                return true;
            }
            return true;
        }

        return true;

    }


}
