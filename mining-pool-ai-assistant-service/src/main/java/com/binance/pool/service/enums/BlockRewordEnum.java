package com.binance.pool.service.enums;

import com.binance.pool.service.util.Constants;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by yyh on 2020/3/28
 */
@Getter
public enum  BlockRewordEnum {
    FIRST(210000l, new BigDecimal("50")),
    SECOND(420000l, new BigDecimal("25")),
    THIRD(630000l, new BigDecimal("12.5")),
    FOURTH(840000l, new BigDecimal("6.25")),
    FIFTH(1050000l, new BigDecimal("3.125")),
    SIXTH(1260000l, new BigDecimal("1.5625")),
    SEVENTH(1470000l, new BigDecimal("0.78125")),
    EIGHTH(1680000l, new BigDecimal("0.390625")),
    NINTH(1890000l, new BigDecimal("0.09765625")),
    TENTH(2100000l, new BigDecimal("0.048828125")),
    ;
    //小于这个块高的，奖励都是reword的值
    private Long height;
    private BigDecimal reword;

    BlockRewordEnum(Long height, BigDecimal reword) {
        this.height = height;
        this.reword = reword;
    }
    public static BigDecimal getReword(Long height){
        //经常用的快速返回不用计算
        if(height<FIRST.getHeight()){
            return FIRST.getReword();
        }else if(height<SECOND.getHeight()){
            return SECOND.getReword();
        }else if(height<THIRD.getHeight()){
            return THIRD.getReword();
        }else if(height<FOURTH.getHeight()){
            return FOURTH.getReword();
        }else if(height<FIFTH.getHeight()){
            return FIFTH.getReword();
        }else if(height<SIXTH.getHeight()){
            return SIXTH.getReword();
        }else if(height<SEVENTH.getHeight()){
            return SEVENTH.getReword();
        }else if(height<EIGHTH.getHeight()){
            return EIGHTH.getReword();
        }else if(height<NINTH.getHeight()){
            return NINTH.getReword();
        }else if(height<TENTH.getHeight()){
            return TENTH.getReword();
        }
        int halfCount = (int)(height/210000)-9;
        BigDecimal reword = TENTH.getReword();
        for (int i=0;i<halfCount;i++){
            reword = reword.divide(new BigDecimal(2),8, RoundingMode.HALF_DOWN);
        }
        if(reword.compareTo(Constants.satoshi)<0){
            return BigDecimal.ZERO;
        }
        return reword;

    }

    public static void main(String[] args) {
        System.out.println(getReword(623276l+1));
        System.out.println(getReword(1470000l-1));
        System.out.println(getReword(1470000l));
        System.out.println(getReword(1890000l-1));
        System.out.println(getReword(1890000l));
        System.out.println(getReword(2100000l-1));
        System.out.println(getReword(2100000l));
        System.out.println(getReword(2100000l+1));
        System.out.println(getReword(5100000l));
    }
}
