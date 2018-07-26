package com.ms.platform.server.config.common.utils;

import java.util.*;
import java.util.zip.CRC32;

import static com.ms.platform.server.config.common.utils.XStringBuilder.*;

public final class TracerStringUtils {

    public static final String   NEWLINE            = "\r\n";

    public static final String   EMPTY_STRING       = "";

    public static final String   EQUAL              = "=";

    public static final String   AND                = "&";

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    /**
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * @param str
     * @param separatorChar
     * @return
     */
    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList<String>();
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                start = ++i;
                continue;
            }
            lastMatch = false;
            match = true;
            i++;
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * @param map
     * @param separator
     * @param prefix
     * @param postfix
     * @param joiner
     * @return
     */
    public static String mapToString(Map<String, String> map, char separator, String prefix, String postfix, char joiner) {
        String emptyMap = (EMPTY_STRING + prefix) + postfix;

        if (map == null) {
            return emptyMap;
        }

        Iterator<String> keysIter = map.keySet().iterator();

        if (!keysIter.hasNext()) {
            return emptyMap;
        }

        String first = keysIter.next();

        if (!keysIter.hasNext()) {
            return first == null ? emptyMap : prefix + first + joiner + map.get(first) + postfix;
        }

        StringBuffer buf = new StringBuffer(256);
        buf.append(prefix);

        if (first != null) {
            buf.append(first).append(joiner).append(map.get(first));
        }

        while (keysIter.hasNext()) {
            String key = keysIter.next();
            buf.append(separator);

            if (key != null) {
                buf.append(key).append(joiner).append(map.get(key));
            }
        }
        buf.append(postfix);
        return buf.toString();
    }

    /**
     * @param map
     * @param separator
     * @param prefix
     * @param postfix
     * @param joiner
     * @param separatorEscape
     * @return
     */
    public static String mapToString(Map<String, String> map, char separator, String prefix, String postfix,
                                     char joiner, String separatorEscape) {
        if (null != map) {
            Map<String, String> mapEscape = new HashMap<String, String>();
            String separatorStr = separator + "";
            for (Map.Entry entry : map.entrySet()) {
                String key = (String) entry.getKey();
                String keyEscape = (key == null) ? null : key.replace(separatorStr, separatorEscape);
                String value = (String) entry.getValue();
                String valueEscape = (value == null) ? null : value.replace(separatorStr, separatorEscape);
                mapEscape.put(keyEscape, valueEscape);
            }
            return mapToString(mapEscape, separator, prefix, postfix, joiner);
        } else {
            return mapToString(map, separator, prefix, postfix, joiner);
        }
    }

    /**
     * @param items
     * @param separator
     * @param prefix
     * @param postfix
     * @return
     */
    public static String arrayToString(Object[] items, char separator, String prefix, String postfix) {
        String emptyArrayString = (EMPTY_STRING + prefix) + postfix;

        // handle null, zero and one elements before building a buffer
        if (items == null) {
            return emptyArrayString;
        }
        if (items.length == 0) {
            return emptyArrayString;
        }

        Object first = items[0];

        if (items.length == 1) {
            return first == null ? emptyArrayString : prefix + first.toString() + postfix;
        }

        StringBuffer buf = new StringBuffer(256);
        buf.append(prefix);

        if (first != null) {
            buf.append(first);
        }

        for (int i = 1; i < items.length; i++) {
            buf.append(separator);
            Object obj = items[i];

            if (obj != null) {
                buf.append(obj);
            }
        }

        buf.append(postfix);
        return buf.toString();
    }

    /**
     * @param items
     * @param separator
     * @param prefix
     * @param postfix
     * @param separatorEscape
     * @return
     */
    public static String arrayToString(Object[] items, char separator, String prefix, String postfix,
                                       String separatorEscape) {
        if (items != null && items.length > 0) {
            String[] itemEscapes = new String[items.length];
            String separatorStr = separator + "";
            for (int i = 0; i < items.length; i++) {
                itemEscapes[i] = items[i].toString().replace(separatorStr, separatorEscape);
            }
            return arrayToString(itemEscapes, separator, prefix, postfix);
        } else {
            return arrayToString(items, separator, prefix, postfix);
        }
    }

    /**
     * @param str
     * @param defaultValue
     * @param appender
     * @return
     */
    public static StringBuilder appendWithBlankCheck(String str, String defaultValue, StringBuilder appender) {
        if (isNotBlank(str)) {
            appender.append(str);
        } else {
            appender.append(defaultValue);
        }
        return appender;
    }

    /**
     * @param obj
     * @param defaultValue
     * @param appender
     * @return
     */
    public static StringBuilder appendWithNullCheck(Object obj, String defaultValue, StringBuilder appender) {
        if (obj != null) {
            appender.append(obj.toString());
        } else {
            appender.append(defaultValue);
        }
        return appender;
    }

    /**
     * 追加日志，同时过滤字符串中的换行为空格，避免导致日志格式解析错误
     */
    public static StringBuilder appendLog(String str, StringBuilder appender) {
        if (str != null) {
            int len = str.length();
            appender.ensureCapacity(appender.length() + len);
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (c == '\n' || c == '\r' || c == '|') {
                    // 用 appender.append(str, start, len) 批量 append 实质也是一个字符一个字符拷贝
                    // 因此此处还是用土办法
                    c = ' ';
                }
                appender.append(c);
            }
        }
        return appender;
    }

    /**
     * 过滤字符串中的换行为空格，避免导致日志格式解析错误
     * 
     * @param str
     * @return
     * @see #appendLog(String, StringBuilder)
     */
    public static String filterInvalidCharacters(String str) {
        StringBuilder appender = new StringBuilder(str.length());
        return appendLog(str, appender).toString();
    }

    /**
     * 对字符串生成摘要，目前使用 CRC32 算法
     * 
     * @param str
     * @return 摘要后的字符串
     */
    public static String digest(String str) {
        CRC32 crc = new CRC32();
        crc.update(str.getBytes());
        return Long.toHexString(crc.getValue());
    }

    /**
     * 将map转成string, 如{"k1":"v1", "k2":"v2"} => k1=v1&k2=v2&
     */
    public static String mapToString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer(XStringBuilder.DEFAULT_BUFFER_SIZE);

        if (map == null) {
            sb.append(TracerStringUtils.EMPTY_STRING);
        } else {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = escapePercentEqualAnd(entry.getKey());
                String val = escapePercentEqualAnd(entry.getValue());
                sb.append(key).append(TracerStringUtils.EQUAL).append(val).append(TracerStringUtils.AND);
            }
        }

        return sb.toString();
    }

    /**
     * 由string转成map, 函数 mapToString 的逆过程
     */
    public static void stringToMap(String str, Map<String, String> map) {
        if (str != null) {
            for (String kv : str.split(TracerStringUtils.AND)) {
                String[] kvs = kv.split(TracerStringUtils.EQUAL);
                if (kvs.length == 2) {
                    map.put(kvs[0], unescapeEqualAndPercent(kvs[1]));
                }
            }
        }
    }

    /**
     * 替换str中的"&"，"=" 和 "%"
     */
    private static String escapePercentEqualAnd(String str) {
        // 必须先对 % 做转义
        return escape(escape(escape(str, PERCENT, PERCENT_ESCAPE), AND_SEPARATOR, AND_SEPARATOR_ESCAPE),
                      EQUAL_SEPARATOR, EQUAL_SEPARATOR_ESCAPE);
    }

    /**
     * 将 str 中被转义的 & ， = 和 % 转义回来
     */
    private static String unescapeEqualAndPercent(String str) {
        // 必须最后才对 % 做转义
        return escape(escape(escape(str, EQUAL_SEPARATOR_ESCAPE, EQUAL_SEPARATOR), AND_SEPARATOR_ESCAPE, AND_SEPARATOR),
                      PERCENT_ESCAPE, PERCENT);
    }

    /**
     * 将str中的oldStr替换为newStr
     */
    private static String escape(String str, String oldStr, String newStr) {
        if (str == null) {
            return TracerStringUtils.EMPTY_STRING;
        }
        return str.replace(oldStr, newStr);
    }
}
