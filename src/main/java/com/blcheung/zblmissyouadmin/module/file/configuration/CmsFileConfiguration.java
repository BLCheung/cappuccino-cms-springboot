package com.blcheung.zblmissyouadmin.module.file.configuration;

import com.blcheung.zblmissyouadmin.module.file.LocalUploader;
import com.blcheung.zblmissyouadmin.module.file.QiniuUploader;
import com.blcheung.zblmissyouadmin.module.file.common.Uploader;
import com.blcheung.zblmissyouadmin.module.file.kit.FileKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件上传相关配置类
 *
 * @author BLCheung
 * @date 2022/1/15 2:34 上午
 */
@Configuration(proxyBeanMethods = false)
public class CmsFileConfiguration implements WebMvcConfigurer {

    @Autowired
    private CmsFileProperties cmsFileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(this.cmsFileProperties.getServerPath())
                .addResourceLocations("file:" + FileKit.getAbsolutePath(this.cmsFileProperties.getLocalPath()) + "/");
    }

    @Bean
    @Order
    @ConditionalOnMissingBean
    public Uploader uploader() {
        return new LocalUploader();
        // return new QiniuUploader();
    }
}
