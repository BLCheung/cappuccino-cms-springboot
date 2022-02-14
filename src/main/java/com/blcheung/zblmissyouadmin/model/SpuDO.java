package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.zblmissyouadmin.model.BaseDO;

import java.io.Serializable;

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
@TableName("spu")
public class SpuDO extends BaseDO {

    private static final long serialVersionUID = 1L;

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
