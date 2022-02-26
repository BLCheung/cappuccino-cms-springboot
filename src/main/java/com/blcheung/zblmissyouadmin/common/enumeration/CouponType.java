package com.blcheung.zblmissyouadmin.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

public enum CouponType implements IEnum<Integer> {
    FULL_MINUS(1, "满减券"),
    FULL_OFF(2, "满减折扣券"),
    NO_THRESHOLD(0, "无门槛");

    private final Integer value;

    CouponType(Integer value, String desc) {
        this.value = value;
    }

    public static Optional<CouponType> toType(Integer value) {
        if (value == null) return Optional.empty();
        return Stream.of(CouponType.values())
                     .filter(couponType -> couponType.getValue()
                                                     .equals(value))
                     .findAny();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
