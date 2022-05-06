package com.blcheung.cappuccino.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2022/1/30 8:32 下午
 */
@Getter
@Setter
public class BannerDTO {

    @NotBlank
    @Length(min = 1, max = 20)
    private String name;

    @Length(min = 1, max = 30)
    private String title;

    @Length(min = 1, max = 255)
    private String img;

    @Length(min = 1, max = 255)
    private String description;
}
