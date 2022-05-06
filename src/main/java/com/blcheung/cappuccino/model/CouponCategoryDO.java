package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
@TableName("coupon_category")
public class CouponCategoryDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4933444907752220474L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long categoryId;

    private Long couponId;

    public CouponCategoryDO(Long categoryId, Long couponId) {
        this.categoryId = categoryId;
        this.couponId   = couponId;
    }
}
