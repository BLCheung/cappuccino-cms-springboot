package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.util.JSONConverterUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/15 5:34 下午
 */
@Getter
@Setter
public class SkuVO {

    private Long id;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private Integer online;

    private String img;

    private String title;

    private Long spuId;

    private List<SpecKeyValueVO> specs;

    private String code;

    private Integer stock;

    private Long categoryId;

    private Long rootCategoryId;

    private Integer limitBuyCount;

    public void setSpecs(String specs) {
        this.specs = JSONConverterUtil.convertJSONToObject(specs, new TypeReference<List<SpecKeyValueVO>>() {});
    }
}
