package com.lu.framework.helper;

import com.lu.framework.ConfigConstant;
import com.lu.framework.util.PropsUtil;

import java.util.Properties;

/**
 * @author lupeng
 * 2021/3/3
 */
public final class ConfigHelper {
    /**
     * 加载配置文件属性
     */
    private static final Properties CONFIG_PROPS = PropsUtil.loadProp(ConfigConstant.CONFIG_FILE);


    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }
}
