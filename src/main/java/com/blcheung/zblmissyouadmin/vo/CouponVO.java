package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BLCheung
 * @date 2022/2/22 4:17 上午
 */
@Getter
@Setter
public class CouponVO {

    private Long id;

    private String title;

    private Date startTime;

    private Date endTime;

    private String description;

    private BigDecimal fullMoney;

    private BigDecimal minus;

    private BigDecimal rate;

    /**
     * 0. 无门槛券 1.满减券 2.满减折扣券
     */
    private Integer type;

    private Long activityId;

    private String remark;

    private Integer wholeStore;
}
