package com.binance.pool.service.vo.machinegun;

import com.binance.pool.dao.bean.PoolRequestApiConfigBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterceptorVo {
    private String appId;
    private String sign;
    private String appName;
    private Long timestamp;

    private PoolRequestApiConfigBean bean;
}
