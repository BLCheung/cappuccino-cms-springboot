package com.blcheung.zblmissyouadmin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/1 9:01 下午
 */
@Getter
@Setter
public class BannerItemDTO {

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @Length(min = 1, max = 50)
    private String keyword;

    @NotNull
    @Positive
    private Integer type;

    @NotNull
    private Long bannerId;

    @NotBlank
    @Length(min = 1, max = 20)
    private String name;
}
