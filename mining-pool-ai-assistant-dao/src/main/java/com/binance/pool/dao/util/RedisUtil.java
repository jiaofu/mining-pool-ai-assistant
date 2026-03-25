package com.binance.pool.dao.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghui on 2018/6/8.
 */
public class RedisUtil {
    private static RedisUtil redisUtil = null;
    private static Map<String,RedisUtil> redisUtils = Maps.newHashMap();
    /**
     * 获取实例
     *
     * @return
     */
    public static RedisUtil getRedisUtil() {
        if (null != redisUtil) {
            return redisUtil;
        }
        Object object = SpringContextUtil.getBean("redisUtil");
        if (null == object) {
            return null;
        }
        redisUtil = (RedisUtil) object;
        return redisUtil;
    }
    public static RedisUtil getRedisUtil(String region) {
        if (redisUtils.get(region)!=null) {
            return redisUtils.get(region);
        }
        Object object = SpringContextUtil.getBean("redisUtil"+region);
        if (null == object) {
            return null;
        }
        RedisUtil redisUtil = (RedisUtil) object;
        redisUtils.put(region,redisUtil);
        return redisUtil;
    }

    private RedisTemplate<String, String> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, String... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    /**
     * 添加set对象
     * @param key
     * @param values
     * @return
     */
    public Long addSet(String key,String...values){
        try {
            return key == null ? null : redisTemplate.opsForSet().add(key,values);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取全部Set缓存
     * @param key
     * @return
     */
    public Set<String> members(String key){
        try {
            return key == null ? null : redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 设置对象
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(String key, Object value) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueStr);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean setObject(String key, Object value, long time) {
        try {
            String valueStr = JSON.toJSONString(value);
            redisTemplate.opsForValue().set(key, valueStr, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, String value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean setnx(String key,String value){
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 通过key获取对象
     *
     * @param key
     * @param clazz
     * @param
     * @return
     */
    public <T> T getObject(String key, Class<T> clazz) {
        if (!StringUtils.isEmpty(key)) {
            String objJson = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(objJson)) {
                return JSON.parseObject(objJson, clazz);
            }
        }
        return null;
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<? extends Object,? extends Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public Set<Object> hkeys(String key){
        return redisTemplate.opsForHash().keys(key);
    }

    public <T> List<T> hmListObject(String key,Class<T> tClass) {
        List<Object> objectList=redisTemplate.opsForHash().values(key);
        if(CollectionUtils.isEmpty(objectList)){
            return null;
        }
        List<T> list=new ArrayList<>();
        for(Object object:objectList){
            String jsonstr=(String)object;
            T t=JSON.parseObject(jsonstr,tClass);
            list.add(t);
        }
        return  list;
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, String value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean hMultiset(String key, Map<String,String> entities) {
        try {
            redisTemplate.opsForHash().putAll(key,entities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public String hget(String key, String item) {
        return (String)redisTemplate.opsForHash().get(key, item);
    }
    public <T> List<T> hMultiGet(String key,List items,Class<T> tClass) {
        List<Object> objectList=redisTemplate.opsForHash().multiGet(key,items);
        if(CollectionUtils.isEmpty(objectList)){
            return null;
        }
        List<T> list=new ArrayList<>();
        for(Object object:objectList){
            String jsonstr=(String)object;
            T t=JSON.parseObject(jsonstr,tClass);
            list.add(t);
        }
        return  list;
    }
    public List<String> multiGet(List<String> keys){
        List objs = redisTemplate.opsForValue().multiGet(keys);
        return objs;
    }
    public boolean multiSet(Map<String,String> map){
        try {
            redisTemplate.opsForValue().multiSet(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取以firstKey 开头的字符
     * @param firstKey
     * @return
     */
    public  Set<String> keyFirstlikes(String firstKey){
         Set<String> keys = redisTemplate.keys(firstKey + "*");
         return keys;
    }

    public void sendMg(String channel, Object message){
        redisTemplate.convertAndSend(channel,message);
    }


}
