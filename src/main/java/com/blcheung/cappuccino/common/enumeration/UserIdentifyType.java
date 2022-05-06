package com.blcheung.cappuccino.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 用户凭证类型
 *
 * @author BLCheung
 * @date 2021/12/28 12:10 上午
 */
public enum UserIdentifyType implements IEnum<String> {
    USERNAME_PASSWORD("USERNAME_PASSWORD", "用户名密码形式"),
    MICROPROGRAM("MICROPROGRAM", "小程序形式");

    private final String value;

    UserIdentifyType(String value, String desc) {
        this.value = value;
    }

    /**
     * MybatisEnumTypeHandler 转换时调用此方法
     *
     * @return java.lang.String
     * @author BLCheung
     * @date 2021/12/28 12:29 上午
     */
    @Override
    public String getValue() {
        return this.value;
    }
}
