package com.binance.pool.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.binance.pool.dao.bean.PoolBinanceUserBean;
import com.binance.pool.service.cache.UserCacheServer;
import com.binance.pool.service.enums.HttpResponseContextType;
import com.binance.pool.service.enums.ReturnCodeEnum;
import com.binance.pool.service.util.Constants;
import com.binance.pool.service.vo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class UserIdInterceptorImpl implements HandlerInterceptor {

    @Resource
    UserCacheServer userCacheServer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean startBool = request.getRequestURI().toLowerCase().startsWith("/mining-api/v1/private/pool/savings");
        if (!startBool) {
            return true;
        }
        String userId = request.getParameter("userId");
        //log.info(" preHandle 获取的参数:{} ",userId);
        if (StringUtils.isEmpty(userId)) {
            output(response);
            return false;
        }
        Long uid = Long.parseLong(userId);

        PoolBinanceUserBean binanceUserBean = userCacheServer.getPoolBinanceUserBean(uid);
        if (binanceUserBean == null || binanceUserBean.getUidPoolBinance() == null) {
            //log.info(" preHandle 没有找到矿池id， uid为:{} ",userId);
            output(response);
            return false;
        }
        //log.info(" preHandle 获取到矿池的id:{} ",binanceUserBean.getUidPoolBinance());
        request.setAttribute(Constants.UID_POOL_BINANCE, binanceUserBean.getUidPoolBinance());
        return true;

    }

    private void output(HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType(HttpResponseContextType.JSON.getValue());
            response.setCharacterEncoding(Constants.ENCODED_UTF8);
            out = response.getWriter();

            ResultBean resultBean = ResultBean.error(ReturnCodeEnum.UUID_POOL_BINANCE_MISS_ERROR);

            String json = JSONObject.toJSONString(resultBean);
            out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
