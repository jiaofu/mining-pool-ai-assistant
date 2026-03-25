package com.binance.pool.dao.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
/**
 * 联合挖矿的币种，挖到了多少币
 * 使用此Bean要设置tableName
 */
public class UnionMergeMiningBean implements IDynamicTableName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long height;
    private String hash;
    private String address;
    private BigDecimal value;
    private Long time;
    private Long mediantime;
    private Date createAt;
    private Date updatedAt;

    @Transient
    private String tableName;
    @Override
    public String getDynamicTableName() {
        return tableName;
    }
}
