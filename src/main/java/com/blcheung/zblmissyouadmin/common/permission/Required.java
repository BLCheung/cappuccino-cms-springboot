package com.blcheung.zblmissyouadmin.common.permission;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 必须的权限级别标记
 *
 * @author BLCheung
 * @date 2021/12/15 8:44 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Required {

    UserLevel level() default UserLevel.GUEST;
}
