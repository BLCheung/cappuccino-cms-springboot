package com.blcheung.zblmissyouadmin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/19 10:19 下午
 */
@Getter
@Setter
public class ThemeSpuDTO {

    @NotNull
    @Positive
    private Long themeId;

    @NotNull
    @Positive
    private Long spuId;
}
