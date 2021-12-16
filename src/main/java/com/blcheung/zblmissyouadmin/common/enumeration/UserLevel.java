package com.blcheung.zblmissyouadmin.common.enumeration;

import lombok.Getter;

/**
 * 用户级别权限枚举
 *
 * @author BLCheung
 * @date 2021/12/15 8:39 下午
 */
@Getter
public enum UserLevel {
    ADMIN(0, "管理员，最高权限"),
    GROUP(1, "登录且具有相应角色权限"),
    LOGIN(2, "登录即可访问权限"),
    GUEST(3, "游客权限，不限制"),
    REFRESH(4, "刷新令牌权限");

    private final Integer value;

    UserLevel(Integer value, String desc) {
        this.value = value;
    }
}
