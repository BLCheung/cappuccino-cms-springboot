package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.zblmissyouadmin.model.BaseDO;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Getter
@Setter
@TableName("sku")
public class SkuDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    private String img;

    private String title;

    private Long spuId;

    private String specs;

    private String code;

    private Integer stock;

    private Long categoryId;

    private Long rootCategoryId;

    private Integer limitBuyCount;


}
