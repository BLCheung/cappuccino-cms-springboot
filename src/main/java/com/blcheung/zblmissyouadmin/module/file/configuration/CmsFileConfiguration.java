package com.blcheung.zblmissyouadmin.module.file.configuration;

import com.blcheung.zblmissyouadmin.module.file.LocalUploader;
import com.blcheung.zblmissyouadmin.module.file.common.Uploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传相关配置类
 *
 * @author BLCheung
 * @date 2022/1/15 2:34 上午
 */
@Configuration(proxyBeanMethods = false)
public class CmsFileConfiguration {

    @Bean
    public Uploader uploader() {
        return new LocalUploader();
    }
}
