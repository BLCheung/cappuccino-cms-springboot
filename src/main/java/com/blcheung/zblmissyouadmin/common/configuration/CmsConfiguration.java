package com.blcheung.zblmissyouadmin.common.configuration;

import com.blcheung.zblmissyouadmin.common.token.DoubleJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    @Bean
    public DoubleJWT doubleJWT() {
        Long accessTokenExpired = this.cmsProperties.getAccessTokenExpired();
        Long refreshTokenExpired = this.cmsProperties.getRefreshTokenExpired();

        if (accessTokenExpired == null) accessTokenExpired = 60 * 60L;

        if (refreshTokenExpired == null) refreshTokenExpired = 60 * 60 * 24 * 7L;

        return new DoubleJWT(this.cmsProperties.getSecret(), accessTokenExpired, refreshTokenExpired);
    }
}
