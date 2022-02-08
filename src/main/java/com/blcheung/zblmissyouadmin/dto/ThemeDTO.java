package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.common.enumeration.OnlineStatus;
import com.blcheung.zblmissyouadmin.validator.Enum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2022/2/7 11:09 下午
 */
@Getter
@Setter
public class ThemeDTO {

    @NotBlank
    @Length(min = 1, max = 60)
    private String title;

    @Length(max = 255)
    private String description;

    @NotBlank
    @Length(min = 1, max = 30)
    private String name;

    @Length(max = 30)
    private String tplName;

    @Length(max = 255)
    private String entranceImg;

    @Length(max = 255)
    private String extend;

    @Length(max = 255)
    private String internalTopImg;

    @Length(max = 255)
    private String titleImg;

    @Enum(target = OnlineStatus.class, allowNull = false)
    private Integer online;

}
