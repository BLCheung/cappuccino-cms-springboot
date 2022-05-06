package com.blcheung.cappuccino.module.file.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author BLCheung
 * @date 2022/1/15 2:35 上午
 */
@Component
@ConfigurationProperties(prefix = "upload")
@PropertySource(value = "classpath:com/blcheung/cappuccino/module/file/config.yml",
                encoding = "UTF-8",
                factory = YmlPropertySourceFactory.class)
public class CmsFileProperties {

    private String domain;

    private String localPath;

    private String serverPath;

    private Integer count;

    private String[] whitelist;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getServerPath() {
        return serverPath;
    }

    public void setServerPath(String serverPath) {
        this.serverPath = serverPath;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String[] getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String[] whitelist) {
        this.whitelist = whitelist;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMaxRequestSize() {
        return maxRequestSize;
    }

    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }
}
