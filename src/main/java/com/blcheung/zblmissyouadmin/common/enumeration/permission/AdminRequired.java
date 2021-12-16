package com.blcheung.zblmissyouadmin.common.enumeration.permission;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 仅管理员权限才可访问权限标记
 *
 * @author BLCheung
 * @date 2021/12/15 10:21 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Required(level = UserLevel.ADMIN)
public @interface AdminRequired {}
