package com.binance.pool.dao.bean.coinstats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stats_pool_day")
public class StatsPoolDayBean extends StatsBean {

    private Long day;
    private Double lucky;

}
