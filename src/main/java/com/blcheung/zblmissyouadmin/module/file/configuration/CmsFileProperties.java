package com.blcheung.zblmissyouadmin.module.file.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author BLCheung
 * @date 2022/1/15 2:35 上午
 */
@ConfigurationProperties(prefix = "upload")
@PropertySource("classpath:com/blcheung/zblmissyouadmin/module/file/config.yml")
public class CmsFileProperties {

    private String domain;

    private String localPath;

    private String serverPath;

    private String singleMaxLimit;

    private Integer count;

    private String[] whitelist;

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

    public String getSingleMaxLimit() {
        return singleMaxLimit;
    }

    public void setSingleMaxLimit(String singleMaxLimit) {
        this.singleMaxLimit = singleMaxLimit;
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
}
