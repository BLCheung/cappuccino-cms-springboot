package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-28
 */
@Getter
@Setter
@TableName("user_coupon")
public class UserCouponDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long couponId;

    /**
     * 0: 未使用，1：已使用， 2：已过期
     */
    private Integer status;

    private Long orderId;

}
