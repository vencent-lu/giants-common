package com.giants.common.lang;

/**
 * @author vencent.lu
 */
public class NamingConventionsUtils {

    public static String initialsLowercase(String namingStr) {
        String initals = new String(new char[]{namingStr.charAt(0)});
        return namingStr.replaceFirst(initals, initals.toLowerCase());
    }

    public static String getSettingMethodName(String fieldName) {
        String initals = new String(new char[]{fieldName.charAt(0)});
        StringBuffer sb = new StringBuffer("set").append(fieldName
                .replaceFirst(initals, initals.toUpperCase()));
        return sb.toString();
    }

}
