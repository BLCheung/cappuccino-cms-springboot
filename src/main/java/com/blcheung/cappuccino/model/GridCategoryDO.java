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
 * @since 2022-02-06
 */
@Getter
@Setter
@TableName("grid_category")
public class GridCategoryDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String title;

    private String img;

    private String name;

    private Long categoryId;

    private Long rootCategoryId;

}
