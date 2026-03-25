package com.binance.pool.dao.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pool_user")
public class PoolUserBean extends  BaseBean {

    private Long uidBinance;// '币安的uid',
    private String poolUsername;//  '名称(全局唯一)',
    private String remark;//  '备注',
    private Integer miningBaoStatus; //是否开启矿池宝: 0 关闭,1 开启
    private Integer status;// '0 正常',
    private String puidDatabase;//32日内有提交算力的数据源


    @JSONField(serialize = false)
    public List<String> getDatabaseList() {
        if(StringUtils.isBlank(this.puidDatabase)){
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(this.puidDatabase.split(","));
    }
}

