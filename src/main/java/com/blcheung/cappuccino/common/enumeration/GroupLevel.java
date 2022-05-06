package com.blcheung.cappuccino.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author BLCheung
 * @date 2021/12/21 12:45 上午
 */
public enum GroupLevel implements IEnum<Integer> {
    ROOT(0, "超级管理员组"),
    ADMIN(1, "管理员组"),
    USER(2, "普通用户组"),
    GUEST(3, "游客组");

    private final Integer value;

    GroupLevel(Integer value, String desc) {
        this.value = value;
    }


    /**
     * MybatisEnumTypeHandler 转换时调用此方法
     *
     * @return java.lang.Integer
     * @author BLCheung
     * @date 2021/12/21 12:50 上午
     */
    @Override
    public Integer getValue() {
        return this.value;
    }
}
