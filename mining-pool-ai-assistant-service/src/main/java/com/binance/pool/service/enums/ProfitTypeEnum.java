package com.binance.pool.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author yyh
 * @date 2020/3/13
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ProfitTypeEnum {

    MINING("挖矿",0),
    UNION_MINING("联合挖矿",1),
    //收益转让时，活动奖励也按100%的
    REWARD("活动奖励 ",2),
    //收益转让时，返点也按100%的返
    REBATE("返点",3),

    GUN_REWARD("机枪奖励",4),
    ADDRESS_MINING("地址挖矿",5),

    //只转让收益挖矿部分
    PROFIT_TRANSFER("收益转让",6),
    //矿池宝也只转入收益转让后的
    MINING_LENDING("矿池宝",7),

    //只有全部转让的账单，才记录为已转让
    TRANSFERRED("已转让",8),
    //给聚矿转账的记录
    JUKUANG_PROFIT("聚矿",9),
    //机枪池其他币种挖矿收益 仅记录用
    SMART_POOL("机枪池结算",10),
    //邀请返佣的账单puid为被邀请人矿池钱包id
    RETURN_COMMISSION("邀请返佣",11),
    RETURN_CASH("好友返现",12),


    //矿池宝（申购-赎回）转给量化账户
    POOL_SAVINGS_ACCOUNT("矿池宝量化账户打钱-活期",13),
    //矿池宝用户赎回 puid为赎回request的targetId
    POOL_SAVINGS_USER_REDEEM("矿池宝赎回-活期",14),
    //矿池宝总计赎回
    POOL_SAVINGS_REDEEM("从量化账户赎回-活期",15),
    //矿池宝（申购-赎回）转给量化账户
    POOL_SAVINGS_REGULAR1("矿池宝量化账户打钱-定期I",16),
    //矿池宝用户赎回 puid为赎回request的targetId
    POOL_SAVINGS_USER_REDEEM_REGULAR1("矿池宝赎回-定期I",17),
    //矿池宝总计赎回
    POOL_SAVINGS_REDEEM_REGULAR1("从量化账户赎回-定期I",18),

    //预定额外5个矿池宝定期相关type，下一个账单类型从31开始

    //后台显示收益转让，网站页面显示矿池钱包
    HASH_TRANSFER("收益转让",31),
    /**start 算力转让前端显示，不会出现在数据库里的 start*/
    //算力转让产生的0，5的账单显示为算力转让
    HASH_TRANSFER_MINING("算力转让-矿池钱包",32),
    //算力转让产生的矿池宝的账单显示为算力转让
    HASH_TRANSFER_SAVINGS("算力转让-矿池宝",33),
    /**end 算力转让前端显示，不会出现在数据库里的 end*/
    ;
    private String desc;
    private Integer type;


}
