package com.blcheung.zblmissyouadmin.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/10 2:57 上午
 */
@Getter
@Setter
public class SpuVO {

    private Long id;

    private String title;

    private String subtitle;

    private Long categoryId;

    private Long rootCategoryId;

    private Integer online;

    /**
     * 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格
     */
    private String price;

    /**
     * 某种规格可以直接附加单品图片
     */
    private Long sketchSpecId;

    /**
     * 默认选中的sku
     */
    private Long defaultSkuId;

    private String img;

    private String discountPrice;

    private String description;

    private String tags;

    private Integer isTest;

    private String spuThemeImg;

    private String forThemeImg;
}
