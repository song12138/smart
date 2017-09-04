package org.common.helper;

import org.common.constant.ConfigConstant;
import org.common.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 * Created by paul on 2017/8/21.
 */
public class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProperties(ConfigConstant.CONFIG_FILE);

    public static String getJdbcriver(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

   public static String getBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.BASE_PACKAGE);
    }

   public static String getJspPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.WEB_JSP_PATH);
    }

    public static String getStaticPath(){
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.WEB_STATIC_PATH);
    }






}
