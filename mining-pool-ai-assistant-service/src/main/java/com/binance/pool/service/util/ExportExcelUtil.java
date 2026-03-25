package com.binance.pool.service.util;

import com.binance.pool.service.enums.HashRateHumanEnum;
import com.binance.pool.service.vo.invitation.PoolUserInvitationRecordRet;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

public class ExportExcelUtil {

    public   static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
    public static HashRateHumanEnum getHumanEnum(long algoId){
        HashRateHumanEnum humanEnum = HashRateHumanEnum.T_SYMBOL;
        if (algoId == 2) {
            humanEnum = HashRateHumanEnum.M_SYMBOL;
        } else if (algoId == 3) {
            humanEnum = HashRateHumanEnum.G_SYMBOL;
        } else  if(algoId ==4){
            humanEnum = HashRateHumanEnum.M_SYMBOL;
        } else  if(algoId ==5){
            humanEnum = HashRateHumanEnum.K_SOL;
        }else if (algoId == 6) {
            humanEnum = HashRateHumanEnum.M_SYMBOL;
        }else if (algoId == 7) {
            humanEnum = HashRateHumanEnum.M_SYMBOL;
        }else if (algoId == 8) {
            humanEnum = HashRateHumanEnum.G_SYMBOL;
        }else if (algoId == 9) {
            humanEnum = HashRateHumanEnum.T_SYMBOL;
        }else if (algoId == 10) {
            humanEnum = HashRateHumanEnum.T_SYMBOL;
        }else if (algoId == 11) {
            humanEnum = HashRateHumanEnum.M_SYMBOL;
        }else if (algoId == 12) {
            humanEnum = HashRateHumanEnum.T_SYMBOL;
        }
        return humanEnum;
    }

    public static String getTotalReword(PoolUserInvitationRecordRet record) {
        Map<String, String> rewardMap = record.getReward();
        StringBuilder sb = new StringBuilder();
        if (rewardMap != null) {
            for (String coinName : rewardMap.keySet()) {
                sb.append(rewardMap.get(coinName)).append(coinName);
                sb.append(",");
            }
        }
        String result = StringUtils.isEmpty(sb) ? "" : sb.substring(0, sb.length() - 1);
        if (StringUtils.isEmpty(result)) {
            result = "0";
        }
        return result;
    }
}
