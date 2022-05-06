package com.blcheung.cappuccino.dto;

import com.blcheung.cappuccino.common.enumeration.OnlineStatus;
import com.blcheung.cappuccino.common.enumeration.RootStatus;
import com.blcheung.cappuccino.validator.Enum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/2/4 11:58 下午
 */
@Getter
@Setter
public class CategoryDTO {

    @NotBlank
    @Length(min = 1, max = 128)
    private String name;

    @Length(max = 255)
    private String description;

    @Enum(target = RootStatus.class, allowNull = false)
    private Integer isRoot;

    @Positive
    private Long parentId;

    @Length(max = 255)
    private String img;

    @Positive
    private Integer index;

    @Enum(target = OnlineStatus.class)
    private Integer online;

    @Positive
    private Integer level;
}
