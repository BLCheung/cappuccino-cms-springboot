package com.blcheung.cappuccino.dto.cms;

import com.blcheung.cappuccino.validator.EqualField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author BLCheung
 * @date 2022/1/28 9:42 下午
 */
@Getter
@Setter
@EqualField(field = "newPassword", confirmField = "confirmPassword", message = "{password.equal_field}")
public class UpdateUserPasswordDTO {
    /**
     * 原密码
     */
    @NotBlank(message = "{password.not_blank}")
    private String password;

    /**
     * 新密码
     */
    @NotBlank(message = "{password.not_blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.pattern}")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "{password.confirm.not_blank}")
    private String confirmPassword;

}
