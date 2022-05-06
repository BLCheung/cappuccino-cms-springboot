package com.blcheung.cappuccino.dto;

import com.blcheung.cappuccino.common.enumeration.OrderStatus;
import com.blcheung.cappuccino.validator.Enum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/28 3:35 上午
 */
@Getter
@Setter
public class OrderStatusDTO {

    @NotNull
    @Positive
    private Long orderId;

    @Enum(target = OrderStatus.class, allowNull = false)
    private Integer status;
}
