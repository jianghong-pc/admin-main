package com.qianmi.admin.common.configuration;

import com.qianmi.admin.common.util.PropertiesHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.Properties;

/**
 * <p>
 *    读取配置文件配置
 * <p/>
 *
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class PropertiesConfiguration implements InitializingBean {

    private static final String CONFIG_LOCATIONS = "classpath*:/messages/*.properties";

    @Bean
    public Properties configProperties() throws Exception {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setIgnoreResourceNotFound(true);
        bean.setLocalOverride(true);
        bean.setLocations(resolver.getResources(CONFIG_LOCATIONS));
        bean.setSingleton(true);
        bean.afterPropertiesSet();

        return bean.getObject();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PropertiesHandler.loadConfigProperties(configProperties());
    }
}
