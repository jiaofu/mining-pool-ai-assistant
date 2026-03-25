package com.binance.pool.dao.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pool_coin")
public class PoolCoinBean extends BaseBean {


    private String name; // '币种,  //币种全称:bitcoin'
    private String sign;//'描述符号',
    private String symbol;//名称 //币种名称缩写:btc
    private Integer scale; //'小数点',
    private Integer status;  // '0:使用 1:停止',
    private Integer coinType; //0:挖矿币种 1:联合挖矿币种 2:奖励币种
    private Long unionAlgoId;//联合挖矿对应算法。
    private Integer poolIndex;//排序字段
    private String coinDatabase;// 数据库名字
    private Integer settleType; //  0:只能内部结算，1:只能外部地址结算，2:同时可以内外部结算'


    public String getCoinDatabase() {
        return coinDatabase;
    }


    public void setCoinDatabase(String coinDatabase) {
        this.coinDatabase = coinDatabase;
    }

    @JSONField(serialize = false)
    public List<String> getCoinDatabaseList() {
        if(StringUtils.isBlank(this.coinDatabase)){
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(this.coinDatabase.split(","));
    }
    @JSONField(serialize = false)
    public Set<String> getCoinDatabaseSet() {
        return Sets.newHashSet(getCoinDatabaseList());
    }

    public static void main(String[] args) {
        PoolCoinBean coinBean = new PoolCoinBean();
        coinBean.setCoinDatabase("china,set,set");
        System.out.println(coinBean.getCoinDatabaseList());
        System.out.println(coinBean.getCoinDatabaseSet());
    }
}
