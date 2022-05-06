package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-31
 */
@Getter
@Setter
@TableName("banner_item")
public class BannerItemDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String img;

    private String keyword;

    private Integer type;

    private Long bannerId;

    private String name;
}
