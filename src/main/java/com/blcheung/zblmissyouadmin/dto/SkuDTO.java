package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.common.enumeration.OnlineStatus;
import com.blcheung.zblmissyouadmin.validator.Enum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/16 1:08 上午
 */
@Getter
@Setter
public class SkuDTO {

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal price;

    @DecimalMin(value = "0.00")
    private BigDecimal discountPrice;

    @Enum(target = OnlineStatus.class)
    private Integer online;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @NotBlank
    @Length(min = 1, max = 255)
    private String title;

    @NotNull
    @Positive
    private Long spuId;

    @NotNull
    @Positive
    @Min(1)
    private Integer stock;

    @Valid
    @NotNull
    private List<SpecSelectorDTO> specs;

    @Positive
    @Min(1)
    private Integer limitBuyCount;
}
