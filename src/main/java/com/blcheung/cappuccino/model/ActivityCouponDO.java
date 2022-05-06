package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-22
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("activity_coupon")
public class ActivityCouponDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8188894829303878717L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long couponId;

    private Long activityId;

    public ActivityCouponDO(Long couponId, Long activityId) {
        this.couponId   = couponId;
        this.activityId = activityId;
    }
}
