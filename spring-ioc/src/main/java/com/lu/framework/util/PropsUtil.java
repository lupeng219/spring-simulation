package com.lu.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取文件
 *
 * @author lupeng
 * 2021/3/3
 */
public final class PropsUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProp(String fileName) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = ClassUtil.getClassloader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException("fileName" + fileName + "not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            LOGGER.error("error:" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    LOGGER.error("error:" + e);
                }
            }
        }
        return properties;
    }

    public static String getString (Properties properties, String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }
}
