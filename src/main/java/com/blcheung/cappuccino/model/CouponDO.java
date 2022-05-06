package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-21
 */
@Getter
@Setter
@TableName("coupon")
public class CouponDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    /**
     * 0.无门槛券 1.满减券 2.满减折扣券
     */
    @TableField(value = "`type`")
    private Integer type;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long activityId;

    private String remark;

    private Integer wholeStore;

}
