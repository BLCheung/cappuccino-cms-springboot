package com.blcheung.cappuccino.dto.cms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2022/1/27 4:12 上午
 */
@Getter
@Setter
public class UpdateGroupDTO {

    @NotBlank(message = "{group.name.not_blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    @Length(max = 255, message = "{group.info.length}")
    private String info;
}
