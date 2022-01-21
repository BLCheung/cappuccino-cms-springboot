package com.blcheung.zblmissyouadmin.dto.cms;

import com.blcheung.zblmissyouadmin.validator.EqualField;
import com.blcheung.zblmissyouadmin.validator.LongList;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/11 12:59 上午
 */
@Getter
@Setter
@EqualField(field = "password", confirmField = "confirmPassword", message = "{password.equal_field}")
public class RegisterUserDTO {

    @NotBlank(message = "{username.not_blank}")
    @Length(min = 2, max = 6, message = "{username.length}")
    private String username;

    @NotBlank(message = "{email.not_blank}")
    @Email
    private String email;

    @NotBlank(message = "{password.not_blank}")
    @Pattern(regexp = "^[A-Za-z0-9_*&$#@]{6,22}$", message = "{password.pattern}")
    private String password;

    @NotBlank(message = "{password.confirm.not_blank}")
    private String confirmPassword;

    @LongList(allowBlank = true, message = "{group.ids.long_list}")
    private List<Long> groupIds;
}
