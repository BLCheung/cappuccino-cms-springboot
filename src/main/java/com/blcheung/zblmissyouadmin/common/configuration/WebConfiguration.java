package com.blcheung.zblmissyouadmin.common.configuration;

import com.blcheung.zblmissyouadmin.common.interceptor.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author BLCheung
 * @date 2021/12/1 10:45 下午
 */
@Configuration(proxyBeanMethods = false)
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

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
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authorizeInterceptor);
    }
}
