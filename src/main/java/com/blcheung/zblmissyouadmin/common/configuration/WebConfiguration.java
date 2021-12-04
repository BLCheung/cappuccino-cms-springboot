package com.blcheung.zblmissyouadmin.common.configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BLCheung
 * @date 2021/12/1 10:45 下午
 */
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 跨域
     *
     * @param registry
     * @author BLCheung
     * @date 2021/12/1 11:05 下午
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }


}
