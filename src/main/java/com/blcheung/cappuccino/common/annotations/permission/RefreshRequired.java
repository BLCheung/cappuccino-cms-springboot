package com.blcheung.cappuccino.common.annotations.permission;

import com.blcheung.cappuccino.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 刷新令牌权限标记
 *
 * @author BLCheung
 * @date 2021/12/15 10:21 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Required(level = UserLevel.REFRESH)
public @interface RefreshRequired {}
