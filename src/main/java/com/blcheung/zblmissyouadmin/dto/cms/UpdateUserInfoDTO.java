package com.blcheung.zblmissyouadmin.dto.cms;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * @author BLCheung
 * @date 2022/1/27 10:36 下午
 */
@Getter
@Setter
public class UpdateUserInfoDTO {

    @Length(min = 2, max = 6, message = "{username.length}")
    private String username;

    @Email
    private String email;

    @Length(min = 2, max = 6, message = "{nickname.length}")
    private String nickname;

    @Length(max = 500, message = "{avatar.length}")
    private String avatar;
}
