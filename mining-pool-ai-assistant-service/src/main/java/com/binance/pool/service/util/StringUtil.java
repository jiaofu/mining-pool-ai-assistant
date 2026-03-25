package com.binance.pool.service.util;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yyh
 * @date 2020/3/11
 */
public class StringUtil {

    public static final String SPECIAL_REGEX = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";

    static Pattern p = Pattern.compile(SPECIAL_REGEX);
    public static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    public static final SecureRandom random = new SecureRandom();


    public static final String INTEGER_REGEX = "^[-]?[0-9]*$";
    static Pattern integerPattern = Pattern.compile(INTEGER_REGEX);

    public static boolean isSpecialChar(String str){
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static String getRandomToken() {
        byte bytes[] = new byte[40];
        try {
            random.nextBytes(bytes);
            return encoder.encodeToString(bytes).replaceAll("[-_]", "").substring(0, 40);
        } catch (Exception e) {
            random.nextBytes(bytes);
            return encoder.encodeToString(bytes).replaceAll("[-_]", "").substring(0, 40);
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null)
            return true;
        String tempStr = str.trim();
        if (tempStr.length() == 0)
            return true;
        if (tempStr.equals("null"))
            return true;
        return false;
    }

    public static boolean isIdNumber(String idnumber) {
        if (!StringUtil.isEmpty(idnumber)) {
            String regex = "([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}|[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}(\\d|[Xx]))";
            return idnumber.trim().matches(regex);
        }
        return false;
    }

    public static boolean isNumber(String number) {
        if (!StringUtil.isEmpty(number)) {
            Pattern pattern = Pattern.compile("[0-9]*");
            return pattern.matcher(number).matches();
        }
        return false;
    }

    public static boolean isIntegerString(String str) {
        Matcher m = integerPattern.matcher(str);
        return m.find();
    }

    public static String replaceSpecliaChar(String str) {
        return str.replaceAll(SPECIAL_REGEX, "0");
    }
    /**
     * 校验数字包括小数
     *
     * @param number
     * @return
     */
    public static boolean isNumberDecimal(String number) {
        if (isEmpty(number)) {
            return false;
        }
        if (!StringUtil.isEmpty(number)) {
            return number.matches("^[+]?\\d+(\\.\\d+)?$");
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.printf("");
    }


    public static int nextInt(int max) {
        return random.nextInt(max);
    }
}
