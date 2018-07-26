package com.ms.platform.server.config.common.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Joey on 2017/8/11 0011.
 */
public class RegexValidateUtil {

    public RegexValidateUtil() {
    }

    public static void main(String[] args) {
        System.out.println(isEmail("14_8@qw.df"));
        System.out.println(isMobilePhone("18505046066"));
        System.out.println(checkPassword("@_/ sdsfsu_", 8, 30));
        System.out.println(isCommonCharacter(""));
    }

    public static boolean isEmail(String email) {
        String regex = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return match(regex, email);
    }

    public static boolean isMobilePhone(String mobile) {
        String regex = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
        return match(regex, mobile);
    }

    public static int checkPassword(String password, int start, int end) {
        return password == null?-1:(password.matches("^\\d+$")?1:(password.matches("^[a-zA-Z]+$")?2:(password.matches("^[\\w\\W]{" + start + "," + end + "}$")?3:-1)));
    }

    public static boolean isIP(String str) {
        String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num + "$";
        return match(regex, str);
    }

    public static boolean IsUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return match(regex, str);
    }

    public static boolean IsIDcard(String str) {
        String regex = "(^\\d{18}$)|(^\\d{15}$)";
        return match(regex, str);
    }

    public static boolean IsUpChar(String str) {
        String regex = "^[A-Z]+$";
        return match(regex, str);
    }

    public static boolean IsLowChar(String str) {
        String regex = "^[a-z]+$";
        return match(regex, str);
    }

    public static boolean IsLetter(String str) {
        String regex = "^[A-Za-z]+$";
        return match(regex, str);
    }

    public static boolean IsChinese(String str) {
        String regex = "^[一-龥],{0,}$";
        return match(regex, str);
    }

    public static boolean isSMSVerifyCode(String code) {
        String regex = "^\\d{6}$";
        return match(regex, code);
    }

    public static boolean isImageVerifyCode(String code) {
        String regex = "^.{4}$";
        return match(regex, code);
    }

    public static boolean isGuid(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean isCommonCharacter(String str) {
        String regex = "^[\\w|-]*$";
        return match(regex, str);
    }

    public static boolean IsMonth(String str) {
        String regex = "^(0?[[1-9]|1[0-2])$";
        return match(regex, str);
    }

    public static boolean IsDay(String str) {
        String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
        return match(regex, str);
    }

    public static boolean isDate(String str) {
        String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
        return match(regex, str);
    }

    private static boolean match(String regex, String str) {
        if(str != null && !str.trim().equals("")) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        } else {
            return false;
        }
    }

}
