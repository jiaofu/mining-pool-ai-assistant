package com.binance.pool.service.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig {

    /**  * 是否是测试环境 ，0 是测试环境 1 是线上环境 */

    private Integer online;




    /**
     * db 保存的密钥
     */
    private String dbSecretkey;


    /**
     * 取消算法的显示
     */
    private List<Long> cancelAlgoId;
}
