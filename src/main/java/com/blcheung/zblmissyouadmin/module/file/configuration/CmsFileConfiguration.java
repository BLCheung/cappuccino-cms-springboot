package com.blcheung.zblmissyouadmin.module.file.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传相关配置类
 *
 * @author BLCheung
 * @date 2022/1/15 2:34 上午
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CmsFileProperties.class)
public class CmsFileConfiguration {

    @Autowired
    CmsFileProperties cmsFileProperties;

}
