package com.blcheung.zblmissyouadmin.common.bean;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterMeta;
import com.blcheung.zblmissyouadmin.common.annotations.router.RouterModule;
import com.blcheung.zblmissyouadmin.kit.UserLevelKit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 路由信息收集器
 *
 * @author BLCheung
 * @date 2022/1/1 2:25 上午
 */
public class PermissionMetaCollector implements BeanPostProcessor {

    private final Map<String, RouterInfo> routerMap = new ConcurrentHashMap<>();

    private final Map<String, Map<String, Set<String>>> moduleMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Controller controllerAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Controller.class);
        // 非controller类，无需检查权限信息
        if (controllerAnnotation == null) return bean;

        Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        List<Method> methods = Arrays.stream(allDeclaredMethods)
                                     .filter(this::isRouterMethod)
                                     .collect(Collectors.toList());

        for (Method method : methods) {
            RouterMeta routerMeta = AnnotationUtils.findAnnotation(method, RouterMeta.class);
            if (!ObjectUtils.isEmpty(routerMeta) && routerMeta.mount()) {
                this.addRouterInfo(method, routerMeta);
            }
        }

        return bean;
    }


    private void addRouterInfo(Method method, RouterMeta routerMeta) {
        String className = method.getDeclaringClass()
                                 .getName();
        String methodName = method.getName();

        // 路由唯一标识
        String identity = className + "#" + methodName;

        // 路由模块
        String module;
        RouterModule routerModule = AnnotationUtils.findAnnotation(method.getDeclaringClass(), RouterModule.class);
        if (!ObjectUtils.isEmpty(routerModule)) {
            // 优先使用@RouterMeta指定的module
            module = StringUtils.hasText(routerMeta.module()) ? routerMeta.module() : routerModule.name();
        } else {
            module = StringUtils.hasText(routerMeta.module()) ? routerMeta.module() : className;
        }

        // 路由
        String router = StringUtils.hasText(routerMeta.name()) ? routerMeta.name() : methodName;

        // 路由的权限级别
        UserLevel userLevel = UserLevelKit.findUserLevel(method);
        // UserLevel clazzUserLevel = UserLevelKit.getUserLevel(method.getDeclaringClass());
        // UserLevel methodUserLevel = UserLevelKit.getUserLevel(method);
        // if (!ObjectUtils.isEmpty(clazzUserLevel)) {
        //     // 优先使用method上指定的UserLevel
        //     userLevel = ObjectUtils.isEmpty(methodUserLevel) ? clazzUserLevel : methodUserLevel;
        // } else {
        //     // method没有指定UserLevel则为游客级别
        //     userLevel = ObjectUtils.isEmpty(methodUserLevel) ? UserLevel.GUEST : methodUserLevel;
        // }

        // 构建路由信息
        RouterInfo routerInfo = RouterInfo.builder()
                                          .identity(identity)
                                          .module(module)
                                          .router(router)
                                          .level(userLevel)
                                          .build();

        // 添加进routerMap
        this.addRouterMap(identity, routerInfo);
        // 添加进moduleMap
        this.addModuleMap(module, routerInfo);
    }

    /**
     * 获取所有路由信息
     *
     * @return java.util.Map<java.lang.String, com.blcheung.zblmissyouadmin.common.bean.RouterInfo>
     * @author BLCheung
     * @date 2022/1/5 10:18 下午
     */
    public Map<String, RouterInfo> getRouterMap() {
        return routerMap;
    }

    /**
     * 获取对应的路由信息
     *
     * @param identity
     * @return com.blcheung.zblmissyouadmin.common.bean.RouterInfo
     * @author BLCheung
     * @date 2022/1/5 10:18 下午
     */
    public RouterInfo findRouterInfo(String identity) {
        return this.routerMap.get(identity);
    }

    /**
     * 获取模块化后的路由信息
     *
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, java.util.Set < java.lang.String>>>
     * @author BLCheung
     * @date 2022/1/5 10:18 下午
     */
    public Map<String, Map<String, Set<String>>> getModuleMap() {
        return moduleMap;
    }


    private void addRouterMap(String identity, RouterInfo routerInfo) {
        this.routerMap.put(identity, routerInfo);
    }

    private void addModuleMap(String module, RouterInfo routerInfo) {
        if (this.moduleMap.containsKey(module)) {
            Map<String, Set<String>> moduleRouterMap = this.moduleMap.get(module);
            this.addModuleRouterSet(moduleRouterMap, routerInfo.getRouter(), routerInfo.getIdentity());
            return;
        }
        Map<String, Set<String>> moduleRouterMap = new HashMap<>();
        this.addModuleRouterSet(moduleRouterMap, routerInfo.getRouter(), routerInfo.getIdentity());
        this.moduleMap.put(module, moduleRouterMap);
    }

    private void addModuleRouterSet(Map<String, Set<String>> moduleRouterMap, String routerKey, String routerIdentity) {
        if (moduleRouterMap.containsKey(routerKey)) {
            moduleRouterMap.get(routerKey)
                           .add(routerIdentity);
            return;
        }
        Set<String> routerSet = new HashSet<>();
        routerSet.add(routerIdentity);
        moduleRouterMap.put(routerKey, routerSet);
    }

    private Boolean isRouterMethod(Method method) {
        return !ReflectionUtils.isObjectMethod(method) && !ReflectionUtils.isEqualsMethod(method) &&
               !ReflectionUtils.isHashCodeMethod(method) && !ReflectionUtils.isToStringMethod(method);
    }
}
