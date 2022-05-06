package com.blcheung.cappuccino.dto.cms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author BLCheung
 * @date 2021/12/30 10:33 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "{username.not_blank}")
    private String username;

    @NotBlank(message = "{password.not_blank}")
    private String password;
}
