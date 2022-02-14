package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.common.enumeration.OnlineStatus;
import com.blcheung.zblmissyouadmin.validator.Enum;
import com.blcheung.zblmissyouadmin.validator.Price;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/13 9:33 下午
 */
@Getter
@Setter
public class SpuDTO {

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length(min = 1, max = 800)
    private String subtitle;

    @NotNull
    @Positive
    private Long categoryId;

    @Enum(target = OnlineStatus.class, allowNull = false)
    private Integer online;

    /**
     * 文本型价格，有时候SPU需要展示的是一个范围，或者自定义平均价格
     */
    @NotBlank
    @Price
    @Length(min = 1, max = 20)
    private String price;

    @Price(allowNull = true)
    @Length(max = 20)
    private String discountPrice;

    /**
     * 某种规格可以直接附加单品图片
     */
    @Positive
    private Long sketchSpecId;

    /**
     * 默认选中的sku
     */
    @Positive
    private Long defaultSkuId;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @NotBlank
    @Length(min = 1, max = 255)
    private String forThemeImg;

    @Length(min = 1, max = 255)
    private String description;

    @Length(max = 30)
    private String tags;

    private List<Long> spuKeyIds;

    private List<String> spuTags;

    private List<String> spuImages;

    private List<String> spuDetailImages;

    private Integer isTest;
}
