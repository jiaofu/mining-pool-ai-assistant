package com.binance.pool.config.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yyh on 2020/4/5
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMiningConfig {



    /**
     * db 的 密钥
     */
    private String dbSecretkey;
}
