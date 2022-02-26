package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.common.enumeration.CouponCondition;
import com.blcheung.zblmissyouadmin.common.enumeration.CouponType;
import com.blcheung.zblmissyouadmin.validator.Enum;
import com.blcheung.zblmissyouadmin.validator.LongList;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/22 3:56 上午
 */
@Getter
@Setter
public class CouponDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date endTime;

    @Length(max = 255)
    private String description;

    @DecimalMin(value = "0.00")
    private BigDecimal fullMoney;

    @DecimalMin(value = "0.00")
    private BigDecimal minus;

    @DecimalMin(value = "0.00")
    private BigDecimal rate;

    /**
     * 0.无门槛券 1.满减券 2.满减折扣券
     */
    @Enum(target = CouponType.class, allowNull = false)
    private Integer type;

    /**
     * 关联的活动，可不填
     */
    // @Positive
    // private Long activityId;

    @Length(max = 255)
    private String remark;

    /**
     * 是否全场，无视分类限制（0. 品类范围限制(需要添加相应分类) 1. 适用全场范围）
     */
    @Enum(target = CouponCondition.class, allowNull = false)
    private Integer wholeStore;

    /**
     * 分类范围，与全场互斥
     */
    @NotNull
    @LongList(allowBlank = true)
    private List<Long> categoryIds;
}
