package com.blcheung.zblmissyouadmin.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2021/12/26 9:58 下午
 */
@Getter
@Setter
public class NewAdminGroupDTO {

    /**
     * 管理员分组名称
     *
     * @author BLCheung
     * @date 2021/12/26 9:59 下午
     * @param null
     * @return null
     */
    @NotBlank(message = "{group.name.not_blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    /**
     * 管理员分组描述
     *
     * @author BLCheung
     * @date 2021/12/26 10:00 下午
     * @param null
     * @return null
     */
    @Length(max = 255, message = "{group.info.length}")
    private String info;
}
