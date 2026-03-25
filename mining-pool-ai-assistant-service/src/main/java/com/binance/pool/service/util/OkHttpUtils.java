package com.binance.pool.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtils {

    private static final String sslPassKey = "javax.net.ssl.trustStorePassword";
    private static final String sslStoreKey = "javax.net.ssl.trustStore";

    private static final String HTTP_JSON = "application/json; charset=utf-8";
    private static final String HTTP_FORM = "application/x-www-form-urlencoded; charset=utf-8";

    private static OkHttpClient okHttpClient = null;

    private static OkHttpClient clientAuthentication = null;

    public static void init() {
        String sslPass = System.getProperty(sslPassKey);
        String sslStore = System.getProperty(sslStoreKey);
        //mysql ssl证书会影响 https请求，要先清除掉，获取完数据后再加上证书配置，保证mysql连接
        if (StringUtils.isNotBlank(sslPass)) {
            System.clearProperty(sslPassKey);
            System.clearProperty(sslStoreKey);
        }
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        if (StringUtils.isNotBlank(sslPass)) {
            System.setProperty(sslPassKey, sslPass);
            System.setProperty(sslStoreKey, sslStore);
        }
    }

    /**
     * get请求
     * 对于小文档，响应体上的string()方法非常方便和高效。
     * 但是，如果响应主体很大(大于1 MB)，则应避免string()，
     * 因为它会将整个文档加载到内存中。在这种情况下，将主体处理为流。
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
//                log.info("http GET 请求成功; [url={}]", url);
                return response.body().string();
            } else {
                log.error("Http GET 请求失败; [errorCode = {} , body:{}]", response.code(), response.body().string());
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }

    public static <T> T httpGet(String url, TypeReference<T> type) {
        String result = httpGet(url);
        if (StringUtils.isNotBlank(result)) {
            System.out.println(result);
            return JSON.parseObject(result, type);
        }
        return null;
    }

    public static String httpGet(String url, Map<String, String> headers) {
        if (headers == null || headers.size() == 0) {
            return httpGet(url);
        }

        Request.Builder builder = new Request.Builder();
        headers.forEach((String key, String value) -> builder.header(key, value));
        Request request = builder.get().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("http GET 请求成功; [url={}]", url);
                return response.body().string();
            } else {
                log.error("Http GET 请求失败; [errorxxCode = {} , url={}]", response.code(), url);
            }
        } catch (IOException e) {
            throw new RuntimeException("同步http GET 请求失败,url:" + url, e);
        }
        return null;
    }


    /**
     * 同步 POST调用 无Header
     *
     * @param url
     * @param json
     * @return
     */
    public static String httpPostJson(String url, String json) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(" httpPostJson  confirmBill ：");
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }
        stringBuilder.append(" url " + url);
        stringBuilder.append(" json " + json);
        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            stringBuilder.append(" code 是: " + response.code());
            String result = response.body().string();
            stringBuilder.append(" 结果： " + result);
            if (response.code() == 200) {

                stringBuilder.append(" 结果： " + result);

            } else {
                stringBuilder.append("Http post 请求失败;");
            }
            log.info(stringBuilder.toString());
            return result;
        } catch (IOException e) {
            log.error(stringBuilder.toString(),e);
            throw new RuntimeException(stringBuilder.toString() + "同步http请求失败:", e);
        }
    }


    /**
     * 带密码的批量切换
     * @param url
     * @param json
     * @param admin
     * @param password
     * @return
     */
    public static String httpPostJsonAuthentication(String url, String json, String admin, String password) {
        if (clientAuthentication == null) {
            clientAuthentication = new OkHttpClient.Builder()
                    .connectTimeout(50, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS)
                    .addInterceptor(new BasicAuthInterceptor(admin, password))
                    .build();
        }
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("   httpPostJsonAuthentication ：");
        if (url == null || "".equals(url)) {
            log.error("url为null!");
            return "";
        }
        stringBuilder.append(" url " + url);
        stringBuilder.append(" json " + json);
        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.post(body).build();
        try {
            Response response = clientAuthentication.newCall(request).execute();
            stringBuilder.append(" code 是: " + response.code());
            String result = response.body().string();
            stringBuilder.append(" 结果： " + result);
            if (response.code() == 200) {

                stringBuilder.append(" 结果： " + result);
            } else {
                stringBuilder.append("Http post 请求失败;");
            }
            log.info(stringBuilder.toString());
            return result;
        } catch (IOException e) {
            log.error(stringBuilder.toString(),e);
            throw new RuntimeException(stringBuilder.toString() + "同步http请求失败:", e);
        }
    }

    /**
     * 同步 POST调用 有Header
     *
     * @param url
     * @param headers
     * @param json
     * @return
     */
    public static String httpPostJson(String url, Map<String, String> headers, String json) {

        StringBuilder stringBuilder = new StringBuilder();
        if (headers == null || headers.size() == 0) {
            return httpPostJson(url, json);
        }


        stringBuilder.append(" url " + url);
        stringBuilder.append(" json " + json);
        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder requestBuilder = new Request.Builder().url(url);
        headers.forEach((k, v) -> requestBuilder.addHeader(k, v));
        Request request = requestBuilder.post(body).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            stringBuilder.append(" code 是: " + response.code());
            String result = response.body().string();
            stringBuilder.append(" 结果： " + result);
            if (response.code() == 200) {

                stringBuilder.append(" 结果： " + result);

            } else {
                stringBuilder.append("Http post 请求失败;");
            }
            log.info(stringBuilder.toString());
            return result;
        } catch (IOException e) {
            log.error(stringBuilder.toString(),e);
            throw new RuntimeException(stringBuilder.toString() + "同步http请求失败:", e);
        }
    }

    /**
     * 提交表单
     *
     * @param url
     * @param content
     * @param headers
     * @return
     */
    public static String postDataByForm(String url, String content, Map<String, String> headers) {
        MediaType JSON = MediaType.parse(HTTP_JSON);
        RequestBody body = RequestBody.create(JSON, content);

        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (headers != null && headers.size() > 0) {
            headers.forEach((k, v) -> requestBuilder.addHeader(k, v));
        }
        Request request = requestBuilder
                .post(body)
                .build();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                log.info("postDataByForm; [postUrl={}, requestContent={}, responseCode={}]", url, content, response.code());
                return response.body().string();
            } else {
                log.error("Http Post Form请求失败,[url={}, param={}]", url, content);
            }
        } catch (IOException e) {
            log.error("Http Post Form请求失败,[url={}, param={}]", url, content, e);
            throw new RuntimeException("Http Post Form请求失败,url:" + url);
        }
        return null;
    }


    private static String hmacsha256(String message, String secretKey) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] result = sha256_HMAC.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(result);
    }
    /*public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("miningAccount", "我这是测试的2");
        map.put("hashRate", "1000");
        map.put("threshold",  "20");
        map.put("activeMiners", "800");
        map.put("activeMinerCount",  "60");
        SendMsgArg sendMsgArg = SendMsgArg.builder().userId("10000015").
                antiPhishingCode(null).
                recipient("18639817305").
                needSendTimesCheck(false).
                needIpCheck(false).
                data(map).mobileCode("86").
                tplCode("mining_alert").build();


        System.out.println(JSONObject.toJSONString(sendMsgArg));
        Map<String, String> testHeaders = new HashMap<>();

        testHeaders.put("x-gray-env","dev");

        String result = OkHttpUtils.httpPostJson("http://internal-tk-dev-mpapi-alb-1662771753.ap-northeast-1.elb.amazonaws.com:9132" + "/mpapi/v1/message/send-msg", testHeaders, JSON.toJSONString(sendMsgArg));
        System.out.println(result);


    }*/
}
