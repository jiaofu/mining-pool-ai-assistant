package com.binance.pool.service.util;

import com.binance.pool.service.config.PoolDataConfig;
import com.binance.pool.service.enums.ApiBtcComRegionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yyh on 2020/4/23
 */
@Slf4j
public class ApiUtil {
    public static String getBtcComUrl(String uri, Map<String, String> map, PoolDataConfig poolDataConfig) {
        map.put("nonce", StringUtil.getRandomToken());
        map.put("app_id",poolDataConfig.getAppId());
        map.put("app_name",poolDataConfig.getAppName());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("sign",getSign(map,poolDataConfig.getSecretKey()));
        ApiBtcComRegionEnum regionEnum = ApiBtcComRegionEnum.parseEnum(poolDataConfig.getRegion());
        return regionEnum.getUrl().concat(uri);

    }

    public static Map<String,String> getMyComUrl(String uri, Map<String, String> map) {

        map.put("app_id",map.get("app_id"));
        map.put("app_name",map.get("app_name"));
        map.put("timestamp", map.get("timestamp"));
        //String header = getSign(map,"dc01e34236494425a063f2a991a13b35");
        String header = getSign(map,"7c5090b8013c4bcda48d1b256539f7a4");
        // map.put("sign",getSign(map,"dc01e34236494425a063f2a991a13b35"));
        String url = uri.concat(getParam(map));
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("url",url);
        stringMap.put(Constants.SIGN,header);
        return stringMap;


    }
    private static String getParam(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        for (Map.Entry<String,String> entry:map.entrySet()){
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0,sb.length()-1);
    }
    public static String getSign(Map<String, String> map,String secretKey) {

        Collection<String> values = map.values();
        String message = StringUtils.join(values,"|");
        try {
            return hmacsha256(message,secretKey);
        } catch (Exception e) {
            log.error("hmacsha256 found an error:",e);
        }
        return "";
    }
    private static String hmacsha256(String message, String secretKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] result = sha256_HMAC.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(result);
    }

    public static void main(String[] args) {
        try {
            String message = "496d4cdae758468cb01e6c5fe91d7b26|invitation|1|1697427558942|234|123";
            String secretKey = "d0002f51d79f4ca6bade1fb2bc98e00c";
            String result =  hmacsha256(message,secretKey);
            log.info(" message:{} secretKe:{} result:{}",message,secretKey,result);
        }catch (Exception ex){

        }
   }
}
