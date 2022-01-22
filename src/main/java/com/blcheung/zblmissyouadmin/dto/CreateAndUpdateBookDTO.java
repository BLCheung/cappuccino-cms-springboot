package com.blcheung.zblmissyouadmin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author BLCheung
 * @date 2022/1/22 11:53 下午
 */
@Getter
@Setter
public class CreateAndUpdateBookDTO {

    @NotEmpty
    @Length(min = 1, max = 50)
    private String title;

    @NotEmpty
    @Length(min = 1, max = 50)
    private String author;

    @NotEmpty
    @Length(min = 1, max = 250)
    private String summary;

    @Length(max = 128)
    private String image;
}
