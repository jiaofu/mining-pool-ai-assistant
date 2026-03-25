package com.binance.pool.dao.mapper.pool;

import com.binance.pool.dao.bean.PoolUserBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<PoolUserBean> {


    @Select("SELECT * FROM  pool_user WHERE uid_binance = #{uidBinance} ")
    List<PoolUserBean> getPoolAlgoCoinBeans(@Param("uidBinance") long uidBinance);

    @Select("SELECT * FROM  pool_user WHERE pool_username = #{poolUsername} limit 1")
    PoolUserBean getUsersByPoolName(@Param("poolUsername") String poolUsername);

}
