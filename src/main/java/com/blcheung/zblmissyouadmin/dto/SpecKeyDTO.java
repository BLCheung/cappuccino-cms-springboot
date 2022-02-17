package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.common.enumeration.StandardType;
import com.blcheung.zblmissyouadmin.validator.Enum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2022/2/17 8:28 下午
 */
@Getter
@Setter
public class SpecKeyDTO {

    @NotBlank
    @Length(min = 1, max = 100)
    private String name;

    @Length(max = 30)
    private String unit;

    @Enum(target = StandardType.class)
    private Integer standard;

    @Length(max = 255)
    private String description;
}
