package com.blcheung.zblmissyouadmin.common.configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.blcheung.zblmissyouadmin.common.bean.PermissionMetaCollector;
import com.blcheung.zblmissyouadmin.common.interceptor.AuthorizeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author BLCheung
 * @date 2021/12/5 1:43 上午
 */
@Configuration(proxyBeanMethods = false)
public class CommonConfiguration {

    @Bean
    public PermissionMetaCollector permissionMetaCollector() {
        return new PermissionMetaCollector();
    }

    @Bean
    public AuthorizeInterceptor authorizeInterceptor() {
        return new AuthorizeInterceptor();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
