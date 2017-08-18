package org.common.util;

/**
 * @Description 类型转换工具类
 * @Author william[yeemin_shon@163.com]
 * @Date 2017/5/9 0009 17:49
 */
public class CastUtil {

    /**
     * @Description 转换为String型
     * @param  * @param obj
     * @return java.lang.String
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:27
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * @Description 转换为String型
     * @param  * @param obj
     * @param defaultValue
     * @return java.lang.String
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:26
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * @Description 转换为double型
     * @param  * @param obj
     * @return double
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:30
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * @Description 转换为double型（提供默认值）
     * @param  * @param obj
     * @param defaultValue
     * @return double
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:29
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * @Description 转换为long型
     * @param  * @param obj
     * @return long
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:33
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0);
    }

    /**
     * @Description 转换为long型（提供默认值）
     * @param  * @param obj
     * @param defaultValue
     * @return long
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:32
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            try {
                longValue = Long.parseLong(strValue);
            } catch (NumberFormatException e) {
                longValue = defaultValue;
            }
        }
        return longValue;
    }

    /**
     * @Description 转为int型
     * @param  * @param obj
     * @return int
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:36
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * @Description 转为int型（提供默认值）
     * @param  * @param obj
     * @param defaultValue
     * @return int
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:35
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            try {
                intValue = Integer.parseInt(strValue);
            } catch (NumberFormatException e) {
                intValue = defaultValue;
            }
        }
        return intValue;
    }

    /**
     * @Description 转为boolean型
     * @param  * @param obj
     * @return boolean
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:45
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * @Description 转为boolean型（提供默认值）
     * @param  * @param obj
     * @param defaultValue
     * @return boolean
     * @Author william[yeemin_shon@163.com]
     * @Date 2017/5/9 0009 17:44
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }
}
