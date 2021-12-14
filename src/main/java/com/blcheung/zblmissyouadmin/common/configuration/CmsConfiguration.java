package com.blcheung.zblmissyouadmin.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * cms配置项
 *
 * @author BLCheung
 * @date 2021/12/14 10:40 下午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CmsProperties.class)
public class CmsConfiguration {
    @Autowired
    private CmsProperties cmsProperties;


}
