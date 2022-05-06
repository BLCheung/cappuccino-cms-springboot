package com.blcheung.cappuccino.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author BLCheung
 * @date 2022/2/24 12:22 上午
 */
public enum CouponCondition implements IEnum<Integer> {
    WHOLE_STORE(1, "适用全场范围"),
    CATEGORY(0, "适用特定品类范围");

    private final Integer value;

    CouponCondition(Integer value, String desc) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
