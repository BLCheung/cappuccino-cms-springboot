package com.blcheung.cappuccino.common.annotations.permission;

import com.blcheung.cappuccino.common.enumeration.UserLevel;

import java.lang.annotation.*;

/**
 * 仅超级管理员权限才可访问权限标记
 *
 * @author BLCheung
 * @date 2021/12/21 9:35 下午
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Required(level = UserLevel.ROOT)
public @interface RootRequired {}
