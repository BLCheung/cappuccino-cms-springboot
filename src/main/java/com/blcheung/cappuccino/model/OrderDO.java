package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author BLCheung
 * @since 2022-02-27
 */
@Getter
@Setter
@TableName("`order`")
public class OrderDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    /**
     * user表外键
     */
    private Long userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    /**
     * 过期时间
     */
    private Date expiredTime;

    /**
     * 下单时间
     */
    private Date placedTime;

    /**
     * 支付时间
     */
    private Date payTime;

    private String snapImg;

    private String snapTitle;

    private String snapItems;

    private String snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    /**
     * 不太准确的订单状态
     */
    private Integer status;

    private String remark;

    /**
     * 是否为超时支付成功的订单
     */
    private Boolean payInExpired;

}
