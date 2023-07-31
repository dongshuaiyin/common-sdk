package com.dsy.utils;

/**
 * @Author: dongshuaiyin
 * @Description:
 * @Date: 2023/4/23 09:59
 * @Modified by:
 */
public class ConvertUtils {

    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String upperCaseFirstLatter(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }

    /**
     * 首字母小写
     *
     * @param name
     * @return
     */
    public static String lowerCaseFirstLatter(String name) {
        char[] cs = name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    /**
     * 分割符号，按照指定符号分割
     * @param param
     * @param splitSymbol
     * @return
     */
    public static String splitReturnLast(String param, String splitSymbol) {
        int index = param.indexOf(splitSymbol);
        if (index == -1) {
            return param;
        }
        return splitReturnLast(param.substring(index + 1, param.length()), splitSymbol);
    }

}
