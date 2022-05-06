package com.blcheung.cappuccino.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author BLCheung
 * @date 2022/2/17 8:14 下午
 */
@Getter
@Setter
public class SpecValueDTO {

    @NotBlank
    @Length(min = 1, max = 50)
    private String value;

    @NotNull
    private Long specId;

    @Length(max = 255)
    private String extend;
}
