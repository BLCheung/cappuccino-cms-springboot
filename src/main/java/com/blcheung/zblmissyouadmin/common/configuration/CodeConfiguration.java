package com.blcheung.zblmissyouadmin.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码配置
 *
 * @author BLCheung
 * @date 2021/12/8 12:56 上午
 */
@SuppressWarnings("ConfigurationProperties")
@Configuration
@ConfigurationProperties(prefix = "")
@PropertySource(value = "classpath:config/code-message.properties", encoding = "UTF-8")
public class CodeConfiguration {
    private static Map<Integer, String> codes = new HashMap<>();

    public CodeConfiguration() {
    }

    public void setCodes(Map<Integer, String> codes) { CodeConfiguration.codes = codes; }

    public static String getMessage(Integer code)    { return CodeConfiguration.codes.get(code); }
}
