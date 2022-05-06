package com.blcheung.cappuccino.common.enumeration;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 订单状态枚举
 *
 * @author BLCheung
 * @date 2022/2/28 3:36 上午
 */
public enum OrderStatus implements IEnum<Integer> {
    UNPAID(1, "待支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消");

    private final Integer value;

    OrderStatus(Integer value, String desc) {
        this.value = value;
    }

    public Optional<OrderStatus> toType(Integer value) {
        if (value == null) return Optional.empty();
        return Stream.of(OrderStatus.values())
                     .filter(orderStatus -> orderStatus.getValue()
                                                       .equals(value))
                     .findAny();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
