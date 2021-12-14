package com.blcheung.zblmissyouadmin.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author BLCheung
 * @date 2021/12/14 10:40 下午
 */
@ConfigurationProperties(prefix = "missyou.cms")
public class CmsProperties {
    // 秘钥
    private String secret;
    // token过期时长
    private Long   accessTokenExpired;
    // refreshToken过期时长
    private Long   refreshTokenExpired;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getAccessTokenExpired() {
        return accessTokenExpired;
    }

    public void setAccessTokenExpired(Long accessTokenExpired) {
        this.accessTokenExpired = accessTokenExpired;
    }

    public Long getRefreshTokenExpired() {
        return refreshTokenExpired;
    }

    public void setRefreshTokenExpired(Long refreshTokenExpired) {
        this.refreshTokenExpired = refreshTokenExpired;
    }
}
