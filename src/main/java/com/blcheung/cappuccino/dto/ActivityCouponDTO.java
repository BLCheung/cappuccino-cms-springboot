package com.blcheung.cappuccino.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/22 10:12 下午
 */
@Getter
@Setter
public class ActivityCouponDTO {

    @NotNull
    @Positive
    private Long activityId;

    @NotNull
    @Positive
    private Long couponId;
}
