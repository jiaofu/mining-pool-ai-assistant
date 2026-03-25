package com.binance.pool.dao.bean.coinstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats_pool_hour")
public class StatsPoolHourBean   extends StatsBean{
    private Long hour;

}
