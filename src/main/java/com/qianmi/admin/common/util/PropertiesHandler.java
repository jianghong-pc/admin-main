package com.qianmi.admin.common.util;

import java.util.Properties;

public final class PropertiesHandler {
    /**
     * 属性文件
     */
    private static Properties properties;

    private PropertiesHandler() {

    }

    /**
     * 加载异常码对应的属性文件
     *
     * @param props 属性文件对象
     */
    public static void loadConfigProperties(Properties props) {
        properties = props;
    }

    /**
     * 获取属性
     *
     * @param propKey
     * @return
     */
    public static String getProperty(String propKey) {
        return properties.getProperty(propKey);
    }
}
