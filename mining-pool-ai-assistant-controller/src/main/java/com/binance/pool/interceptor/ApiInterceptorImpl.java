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
//                return true;
            response.setCharacterEncoding("UTF-8");
            String url = request.getRequestURI();

            logBuider.append(" ApiInterceptorImpl preHandle").append(",url:").append(url).append(",method:").append(method);

            logBuider.append(",apikey:").append(request.getParameter(Constants.APP_ID));
            logBuider.append(",SIGN:").append(request.getHeader(Constants.SIGN));
            logBuider.append(",APP_NAME:").append(request.getParameter(Constants.APP_NAME));
            logBuider.append(",TIMESTAMP:").append(request.getParameter(Constants.TIMESTAMP));

            // 检查参数
            ResultBean<InterceptorVo> resultBean = checkQueryNamesAndValues(request, handler);
            if (resultBean.getCode() != 0) {
                logBuider.append(" 参数检查失败 ");
                log.error(logBuider.toString());
                throw new PoolParameterException(resultBean.getCode(), resultBean.getMsg());
            }

            ResultBean resultTime = checkInvokTime(resultBean.getData());


            // 时间校验
            if (resultTime.getCode() != 0) {
                logBuider.append(" 时间检查失败 ");
                log.error(logBuider.toString());
                throw new PoolParameterException(resultTime.getCode(), resultTime.getMsg());
            }


            // 账户校验
            ResultBean resultAppId = checkAppId(resultBean.getData());
            if (resultAppId.getCode() != 0) {
                logBuider.append(" 账户检查失败 ");
                log.error(logBuider.toString());
                throw new PoolParameterException(resultAppId.getCode(), resultAppId.getMsg());
            }

            // sign 校验
            ResultBean resulSign = checkSign(request, resultBean.getData());
            if (resulSign.getCode() != 0) {
                logBuider.append(" sign检查失败 ");
                log.error(logBuider.toString());
                throw new PoolParameterException(resulSign.getCode(), resulSign.getMsg());
            }

        }
        log.info(logBuider.toString());
        return true;

    }

    private ResultBean checkInvokTime(InterceptorVo interceptorVo) {
        long timestamp = interceptorVo.getTimestamp();
        long serverTime = System.currentTimeMillis();
        //Constants.DEFAULT_RECVWINDOW
        if (timestamp < (serverTime + 1000) && (serverTime - timestamp) <=  60 * 10000 ) {
            return ResultBean.ok(true);
        }
        return ResultBean.error(ReturnCodeEnum.TIME_ERROR_CALL);
    }

    private ResultBean checkAppId(InterceptorVo interceptorVo) {

        if (interceptorVo.getAppId().equals(interceptorVo.getBean().getAppId())) {
            return ResultBean.ok(true);
        }
        return ResultBean.error(ReturnCodeEnum.USER_APPID_ERROR);
    }

    private ResultBean checkSign(HttpServletRequest request, InterceptorVo interceptorVo) {
        StringBuilder strLog = new StringBuilder();
        strLog.append(" checkSign ");

        Map<String, String> map = new TreeMap<>();

        request.getParameterMap().entrySet().stream().
                filter(it -> !StringUtils.equals(Constants.SIGN, it.getKey())).collect(Collectors.toList());

        request.getParameterMap().entrySet().stream().
                filter(it -> !StringUtils.equals(Constants.SIGN, it.getKey()))
                .forEach(it -> Arrays.stream(it.getValue()).forEach(v -> map.put(it.getKey(), v)));
        Collection<String> values = map.values();

        String sign = ApiUtil.getSign(map, interceptorVo.getBean().getGetUserSecret());
        strLog.append(" 系统拼接的sign: "+sign);
        strLog.append(" header头的sign: "+interceptorVo.getSign());
        //log.info(strLog.toString());
        if (sign.equals(interceptorVo.getSign())) {
            return ResultBean.ok(true);
        }

        String message = StringUtils.join(values,"|");
        strLog.append(" 得到的加密字符串:"+message);
        log.error(strLog.toString());
        return ResultBean.error(ReturnCodeEnum.AUTHENTICATION_FAIL);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


    /**
     * 检查参数
     *
     * @param handler
     * @return
     */
    private ResultBean<InterceptorVo> checkQueryNamesAndValues(HttpServletRequest request, Object handler) {


        String appId = request.getParameter(Constants.APP_ID);
        String sign = request.getHeader(Constants.SIGN);
        String app_name = request.getParameter(Constants.APP_NAME);
        String timestamp = request.getParameter(Constants.TIMESTAMP);

        if (StringUtil.isEmpty(appId)) {
            return ResultBean.errorWithFromMsg(ReturnCodeEnum.PARAM_MISS_PARAMETER, Constants.APP_ID);
        }
        if (StringUtil.isEmpty(sign)) {
            return ResultBean.errorWithFromMsg(ReturnCodeEnum.PARAM_MISS_PARAMETER, Constants.SIGN);
        }
        if (StringUtil.isEmpty(app_name)) {
            return ResultBean.errorWithFromMsg(ReturnCodeEnum.PARAM_MISS_PARAMETER, Constants.APP_NAME);
        }

        Pattern r = Pattern.compile(app_name);
        if (!r.matcher(app_name).matches()) {
            return ResultBean.error(ReturnCodeEnum.PARAM_ERROR_PARAMETER);
        }

        if (StringUtil.isEmpty(timestamp)) {
            return ResultBean.errorWithFromMsg(ReturnCodeEnum.PARAM_MISS_PARAMETER, Constants.TIMESTAMP);
        }
        Long time = 0L;

        try {
            time = Long.parseLong(timestamp);

        } catch (Exception ex) {

            return ResultBean.errorWithFromMsg(ReturnCodeEnum.PARAM_TYPE_PARAMETER, Constants.TIMESTAMP);
        }

        PoolRequestApiConfigBean poolRequestApiConfigBean = userCacheServer.getPoolRequestApiConfigBean(app_name);
        if (poolRequestApiConfigBean == null) {
            return ResultBean.error(ReturnCodeEnum.USER_APPID_ERROR);
        }
        InterceptorVo interceptorVo = InterceptorVo.builder().appId(appId).appName(app_name).sign(sign).timestamp(time).bean(poolRequestApiConfigBean).build();
        return ResultBean.ok(interceptorVo);

    }


}
