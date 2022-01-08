package com.blcheung.zblmissyouadmin.common.permission;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 登录即可访问权限标记
 *
 * @author BLCheung
 * @date 2021/12/15 9:15 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Required(level = UserLevel.LOGIN)
public @interface LoginRequired {}
