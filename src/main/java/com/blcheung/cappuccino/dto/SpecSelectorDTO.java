package com.blcheung.cappuccino.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/16 1:21 上午
 */
@Getter
@Setter
public class SpecSelectorDTO {

    @NotNull
    @Positive
    private Long keyId;

    @NotNull
    @Positive
    private Long valueId;
}
