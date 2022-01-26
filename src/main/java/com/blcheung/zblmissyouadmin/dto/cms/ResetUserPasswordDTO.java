package com.blcheung.zblmissyouadmin.dto.cms;

import com.blcheung.zblmissyouadmin.validator.EqualField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 管理员或超级管理员重置用户密码
 *
 * @author BLCheung
 * @date 2022/1/27 5:36 上午
 */
@Getter
@Setter
@EqualField(field = "password", confirmField = "confirmPassword", message = "{password.equal_field}")
public class ResetUserPasswordDTO {

    @NotBlank(message = "{password.not_blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.pattern}")
    private String password;

    @NotBlank(message = "{password.confirm.not_blank}")
    private String confirmPassword;
}
