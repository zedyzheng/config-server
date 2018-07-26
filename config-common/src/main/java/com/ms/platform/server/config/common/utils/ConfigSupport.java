package com.ms.platform.server.config.common.utils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joey on 2017/8/8 0008.
 */
public class ConfigSupport {

    public static boolean isAppId(String str){
        if(!str.contains(".")){
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-z|.]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isNamespaceName(String str){
        Pattern pattern = Pattern.compile("^[a-z]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isUserName(String str){
        Pattern pattern = Pattern.compile("^[a-z1-9]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static String[] arrayToStrings(List<String> arrays){
        if(null == arrays || arrays.isEmpty()){
            return new String[]{};
        }
        String[] strings = new String[arrays.size()];
        for (int i = 0; i < arrays.size(); i++) {
            strings[i] = arrays.get(i);
        }
        return strings;
    }

}
