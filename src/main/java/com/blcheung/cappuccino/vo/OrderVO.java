package com.blcheung.cappuccino.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BLCheung
 * @date 2022/2/27 11:51 下午
 */
@Getter
@Setter
public class OrderVO {

    private Long id;

    private String orderNo;

    private Long userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private Date createTime;

    private Date placedTime;

    private Date payTime;

    private Date expiredTime;

    private String snapImg;

    private String snapTitle;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private String remark;

    /**
     * 是否为超时支付成功的订单
     */
    private Boolean payInExpired;

}
