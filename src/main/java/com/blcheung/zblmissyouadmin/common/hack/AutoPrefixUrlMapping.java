package com.blcheung.zblmissyouadmin.common.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author BLCheung
 * @date 2021/12/10 2:43 上午
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {
    @Value("${missyou.api-path}")
    private String apiPath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (mappingInfo != null) {
            String prefix = this.getPrefix(handlerType);
            return RequestMappingInfo.paths(prefix)
                                     .build()
                                     .combine(mappingInfo);
        }

        return mappingInfo;
    }

    private String getPrefix(Class<?> handlerType) {
        String packageName = handlerType.getPackage()
                                        .getName();
        String versionDotPath = packageName.replaceAll(this.apiPath, "");
        return versionDotPath.replace(".", "/");
    }
}
