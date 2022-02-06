package com.blcheung.zblmissyouadmin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/6 10:10 下午
 */
@Getter
@Setter
public class GridCategoryDTO {

    @NotBlank
    @Length(min = 1, max = 30)
    private String title;

    @NotBlank
    @Length(min = 1, max = 255)
    private String img;

    @NotBlank
    @Length(min = 1, max = 30)
    private String name;

    @Positive
    @NotNull
    private Long categoryId;

}
