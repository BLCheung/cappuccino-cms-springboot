package com.blcheung.zblmissyouadmin.common.annotations.permission;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 需要对应角色群组访问权限标记
 *
 * @author BLCheung
 * @date 2021/12/15 10:23 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Required(level = UserLevel.GROUP)
public @interface GroupRequired {}
