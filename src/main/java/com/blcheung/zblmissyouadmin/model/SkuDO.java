package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.blcheung.zblmissyouadmin.util.JSONConverterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
@TableName(value = "sku")
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

    @Version
    private Integer version;

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    @JsonIgnore
    public void setSpecs(List<SpecKeyValueDO> specs) {
        this.specs = JSONConverterUtil.convertObjectToJSON(specs);
    }
}
