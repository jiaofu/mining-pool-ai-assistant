package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pool_user_bill")
public class PoolUserBillBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String batchId;;// '分发的流水号',
    //返佣账单 puid为被邀请人矿池钱包id
    //矿池宝本金赎回 puid为赎回request的targetId右移32位
    private Long puid;//'puid',
    private Long originProfitPuid;//'1.8之后记录的收益来源的puid',
    private Long uidPoolBinance;// uid 中矿池钱包的用户
    private Long algoId;// '算法id（算力的算法）',
    private Long coinId;// '币种id(真正发给用户的币种)',
    private Integer paymentType;// '1.7之后 0:普通打款支付，1： 地址打款支付',
    private Integer type;// '0:挖矿 1:联合挖矿 2:活动奖励',
    /**不同账单类型保存的信息：比如矿池宝相关保存的productId，
     * 挖矿外部打款的地址校验码,
     * 机枪奖励如果为地址打款的地址校验码
     * 算力转让的记录为原始puid
     * */
    private String typeInfo;
    /**联合挖矿地址打款，挖矿外部地址打款*/
    private String address;
    private BigDecimal shareAccept;// ' 平均总算力 ',
    private BigDecimal shareStale;// ' 平均总算力 ',
    private BigDecimal shareReject;// ' 平均总算力 ',
    private BigDecimal minerNetworkFee;// 矿工手续费 ',
    private BigDecimal serviceChargeFee;//手续费支出 ',
    private BigDecimal userPayment;// 实际打款数量 ',
    private BigDecimal dayHashRate;//当日总算力
    private BigDecimal earn;//理论收益
    private BigDecimal allRebateFee;//挖矿账单，总返点费率
    private BigDecimal allRebateAmount;//挖矿账单，总返点金额
    private Integer status;//'0:待支付， 1:支付中  2：已支付',
    private Long day;// '账单日期',
    private BigDecimal hashTransfer;//转让的算力
    private BigDecimal transferAmount;//转让的收益
    private Date createdDate;// '创建时间',
    private Date modifyDate; //修改时间',

}
