package org.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**属性文件工具
 * Created by paul on 2017/8/18.
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 读取配置文件内容
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName){
        Properties properties=null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (null == is) {
                    throw new FileNotFoundException(fileName + "file is not found");
            }

            properties = new Properties();
            properties.load(is);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     *      读取字符串类型
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties properties, String key, String defaultValue) {
        String value=defaultValue;
        if(properties.containsKey(key)){
            value = properties.getProperty(key);
        }
        return value;
    }
    /**
     *      读取字符串类型,默认“”
     * @param properties
     * @param key
     * @param
     * @return
     */
    public static String getString(Properties properties, String key) {
        return getString(properties,key,"");
    }

    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        boolean value=defaultValue;
        if (properties.containsKey(key)) {
            value = CastUtil.castBoolean(properties.getProperty(key));
        }
        return value;
    }

    /**
     * 默认false
     * @param properties
     * @param key
     * @return
     */
    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }


    public static int getInt(Properties properties, String key, int defaultValue) {
        int value=defaultValue;
        if (properties.containsKey(key)) {
            value = CastUtil.castInt(properties.getProperty(key));
        }
        return value;
    }

    /**
     * 默认0
     * @param properties
     * @param key
     * @return
     */
    public static int getInt(Properties properties, String key) {
        return getInt(properties,key,0);
    }


}
