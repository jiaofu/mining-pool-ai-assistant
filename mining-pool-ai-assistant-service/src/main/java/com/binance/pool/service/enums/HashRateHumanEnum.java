package com.binance.pool.service.enums;

import com.binance.pool.service.vo.miner.HumanRateVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author yyh
 * @date 2020/3/12
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum HashRateHumanEnum {
    H_SYMBOL(BigDecimal.TEN.pow(3),new BigDecimal(1l),"h/s"),
    K_SYMBOL(BigDecimal.TEN.pow(6),new BigDecimal(1000l),"Kh/s"),
    M_SYMBOL(BigDecimal.TEN.pow(9),new BigDecimal(1000000l),"Mh/s"),
    G_SYMBOL(BigDecimal.TEN.pow(12),new BigDecimal(1000000000l),"Gh/s"),
    T_SYMBOL(BigDecimal.TEN.pow(15),new BigDecimal(1000000000000l),"Th/s"),
    P_SYMBOL(BigDecimal.TEN.pow(18),new BigDecimal(1000000000000000l),"Ph/s"),
    E_SYMBOL(BigDecimal.TEN.pow(18),new BigDecimal(1000000000000000000l),"Eh/s"),
    //Ph/s Eh/s

    H_SOL(BigDecimal.TEN.pow(3), new BigDecimal(1l), "Sol/s"),
    K_SOL(BigDecimal.TEN.pow(6), new BigDecimal(1000l), "KSol/s"),
    M_SOL(BigDecimal.TEN.pow(9), new BigDecimal(1000000l), "MSol/s"),
    G_SOL(BigDecimal.TEN.pow(12), new BigDecimal(1000000000l), "GSol/s"),
    T_SOL(BigDecimal.TEN.pow(15), new BigDecimal(1000000000000l), "TSol/s"),
    P_SOL(BigDecimal.TEN.pow(18), new BigDecimal(1000000000000000l), "PSol/s"),
    E_SOL(BigDecimal.TEN.pow(18), new BigDecimal(1000000000000000000l), "ESol/s"),
    //Ph/s Eh/s
    ;;

    ;
    private BigDecimal max;
    private BigDecimal factor;
    private String symbol;

    public static String getHumanRate(long share){
        return getHumanRate(new BigDecimal(share));
    }
    public static String getHumanRate(BigDecimal share){
        HashRateHumanEnum symbolEnum = parseEnum(share);
        if(symbolEnum == null){
            return "";
        }
        BigDecimal value = share.divide(symbolEnum.getFactor(),2, RoundingMode.HALF_UP);
        return value.stripTrailingZeros().toPlainString()+symbolEnum.getSymbol();
    }

    private static HashRateHumanEnum parseEnum(BigDecimal share){
        if(share == null){
            return null;
        }
        if(share.compareTo(P_SYMBOL.getMax())>0){
            return E_SYMBOL;
        }else if(share.compareTo(T_SYMBOL.getMax())>0){
            return P_SYMBOL;
        }else if(share.compareTo(G_SYMBOL.getMax())>0){
            return T_SYMBOL;
        }else if(share.compareTo(M_SYMBOL.getMax())>0){
            return G_SYMBOL;
        }else if(share.compareTo(K_SYMBOL.getMax())>0){
            return M_SYMBOL;
        }else if(share.compareTo(H_SYMBOL.getMax())>0){
            return K_SYMBOL;
        }
        return H_SYMBOL;
    }


    public static HumanRateVo getHumanRateUnit(BigDecimal share, HashRateHumanEnum humanEnum) {
        HumanRateVo humanRateVo = new HumanRateVo();

        humanRateVo.setUnit(humanEnum.getSymbol());
        if (share == null || humanEnum == null) {
            humanRateVo.setHash("0");
            return humanRateVo;
        }
        BigDecimal value = share.divide(humanEnum.getFactor(), 2, RoundingMode.HALF_UP);
        humanRateVo.setHash(value.stripTrailingZeros().toPlainString());
        return humanRateVo;
    }
    public static void main(String[] args) {
        System.out.println(getHumanRate(9220000250999896491l));
    }
}
