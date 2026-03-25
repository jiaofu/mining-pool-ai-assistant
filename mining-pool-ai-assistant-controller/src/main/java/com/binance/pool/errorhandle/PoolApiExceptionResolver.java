package com.binance.pool.errorhandle;

import com.alibaba.fastjson.JSONObject;
import com.binance.pool.service.enums.HttpResponseContextType;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.exception.PoolParameterException;
import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.PrintWriter;


/**
 * 异常的统一处理处理
 */
@Component
@Order(-10000)
@Slf4j
public class PoolApiExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        String url = "无";
        if (request.getRequestURI() != null) {
            url = request.getRequestURI();
        }
        PrintWriter out = null;
        response.setContentType(HttpResponseContextType.JSON.getValue());
        response.setCharacterEncoding(Constants.ENCODED_UTF8);
        try {
            out = response.getWriter();
            ResultBean resultBean = null;
            //spring 参数异常
            if (ex instanceof BindException) {
                //@Validated 没有通过
                resultBean = ResultBean.error(ReturnCodeEnum.PARAM_ILLEGAL);
            }else if (ex instanceof MissingServletRequestParameterException) {
                resultBean = ResultBean.error(ReturnCodeEnum.PARAM_ILLEGAL);
                resultBean.setMsg(((MissingServletRequestParameterException) ex).getParameterName());
            } else if (ex instanceof PoolParameterException) {
                resultBean = ResultBean.error(ReturnCodeEnum.PARAM_ILLEGAL);
                PoolParameterException parameterException= (PoolParameterException) ex;
                resultBean =  ResultBean.error( parameterException.getCode(),parameterException.getMessage());
                //  log.error("PoolApiExceptionResolver resolveException error" + url + ",method:" + ((HttpRequestMethodNotSupportedException) ex).getMethod() + ",msg:" + ex.getMessage());
            }
            else if (ex instanceof HttpRequestMethodNotSupportedException) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resultBean = ResultBean.error(ReturnCodeEnum.REQUEST_METHOD_ERROR);
                resultBean.setMsg(ex.getMessage());
                log.error("PoolApiExceptionResolver resolveException error" + url + ",method:" + ((HttpRequestMethodNotSupportedException) ex).getMethod() + ",msg:" + ex.getMessage());
            } else if (ex instanceof MethodArgumentTypeMismatchException) {
                resultBean = ResultBean.error(ReturnCodeEnum.PARAM_ILLEGAL);
                resultBean.setMsg("paramType error param:" + ((MethodArgumentTypeMismatchException) ex).getName());
                log.error("PoolApiExceptionResolver resolveException error" + url + ",method:" + ((HttpRequestMethodNotSupportedException) ex).getMethod() + ",msg:" + ex.getMessage());
            }else if(ex instanceof NoResourceFoundException){
                if(url.equalsIgnoreCase("/")){
                    log.info("resolveException NoResourceFoundException url : {}  no resource",url);
                    return null;
                }else {
                    log.error("resolveException NoResourceFoundException url : {}  no resource",url);
                    return null;//使用默认的视图
                }
            }

            else {
                resultBean  = ResultBean.error(ReturnCodeEnum.SYSTEM_ERROR);
                log.error("PoolApiExceptionResolver resolveException error" + url, ex);
            }
            out.print(JSONObject.toJSONString(resultBean));
        } catch (Exception e) {
            log.error("PoolApiExceptionResolver resolveException error" + url, e);
            log.error("PoolApiExceptionResolver resolveException business error" + url, ex);
        }
        ModelAndView modelAndView = new ModelAndView();//此处返回空view 不适用spring 默认的error view
        return modelAndView;
    }
}
