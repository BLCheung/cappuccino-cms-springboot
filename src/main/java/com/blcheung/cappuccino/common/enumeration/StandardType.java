package com.blcheung.cappuccino.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author BLCheung
 * @date 2022/2/16 8:19 下午
 */
public enum StandardType implements IEnum<Integer> {
    STANDARD(1, "标准类型"),
    NON_STANDARD(0, "非标准类型");

    private final Integer value;

    StandardType(Integer value, String desc) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
