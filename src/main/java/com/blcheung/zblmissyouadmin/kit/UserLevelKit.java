package com.blcheung.zblmissyouadmin.kit;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;
import com.blcheung.zblmissyouadmin.common.permission.Required;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 用户级别权限辅助类
 *
 * @author BLCheung
 * @date 2022/1/1 4:00 上午
 */
public class UserLevelKit {

    /**
     * 获取方法上的用户级别权限
     *
     * @param method
     * @return com.blcheung.zblmissyouadmin.common.enumeration.UserLevel
     * @author BLCheung
     * @date 2022/1/1 4:07 上午
     */
    public static UserLevel getUserLevel(Method method) {
        return getUserLevel(method.getAnnotations());
    }

    /**
     * 获取类上的用户级别权限
     *
     * @param clazz
     * @return com.blcheung.zblmissyouadmin.common.enumeration.UserLevel
     * @author BLCheung
     * @date 2022/1/5 9:46 下午
     */
    public static UserLevel getUserLevel(Class<?> clazz) {
        Controller controllerAnnotation = AnnotationUtils.findAnnotation(clazz, Controller.class);
        if (controllerAnnotation == null) return null;

        Annotation[] annotations = clazz.getAnnotations();
        return getUserLevel(annotations);
    }

    /**
     * 获取注解上的用户级别权限
     *
     * @param annotations
     * @return com.blcheung.zblmissyouadmin.common.enumeration.UserLevel
     * @author BLCheung
     * @date 2022/1/1 4:05 上午
     */
    public static UserLevel getUserLevel(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            Required required = annotationType.getAnnotation(Required.class);
            if (required != null) return required.level();
        }

        return null;
    }
}
