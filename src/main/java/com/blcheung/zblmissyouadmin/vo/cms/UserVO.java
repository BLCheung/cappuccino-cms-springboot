package com.blcheung.zblmissyouadmin.vo.cms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/1/23 11:17 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class UserVO {

    private Long id;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;
}
