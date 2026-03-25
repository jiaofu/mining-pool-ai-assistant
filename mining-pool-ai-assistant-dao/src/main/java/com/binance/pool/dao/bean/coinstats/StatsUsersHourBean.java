package com.binance.pool.dao.bean.coinstats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "stats_users_hour")
public class StatsUsersHourBean  extends StatsBean {
    private Long puid;
    private Long hour;

}
